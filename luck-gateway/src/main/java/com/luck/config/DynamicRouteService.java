package com.luck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 动态路由业务类
 *
 * @author QM.JM
 * @description
 * @time 2020/05/08 9:15 PM
 **/
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

   private final RouteDefinitionWriter routeDefinitionWriter;

   private ApplicationEventPublisher publisher;

   @Autowired
   private RouteDefinitionLocator routeDefinitionLocator;

   public DynamicRouteService(RouteDefinitionWriter routeDefinitionWriter) {
      this.routeDefinitionWriter = routeDefinitionWriter;
   }

   @Override
   public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
      this.publisher = applicationEventPublisher;
   }

   /**
    * 增加路由
    */
   public String save(RouteDefinition definition) {
      try {
         routeDefinitionWriter.save(Mono.just(definition)).subscribe();
         this.publisher.publishEvent(new RefreshRoutesEvent(this));
         return "save success";
      } catch (Exception e) {
         e.printStackTrace();
         return "save failure";
      }
   }

   /**
    * 更新路由
    */
   public String update(RouteDefinition definition) {
      try {
         this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
         this.publisher.publishEvent(new RefreshRoutesEvent(this));
         return "update success";
      } catch (Exception e) {
         e.printStackTrace();
         return "update failure";
      }
   }

   /**
    * 更新路由
    */
   public String updateList(List<RouteDefinition> routeDefinitions) {
      // 原始路由
      List<RouteDefinition> originRouteDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
      if (!CollectionUtils.isEmpty(originRouteDefinitions)) {
         originRouteDefinitions.forEach(routeDefinition -> {
            delete(routeDefinition.getId());
         });
      }
      routeDefinitions.forEach(this::update);
      return "update done";
   }

   /**
    * 删除路由
    */
   public String delete(String id) {
      try {
         this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
         this.publisher.publishEvent(new RefreshRoutesEvent(this));
         return "delete success";
      } catch (Exception e) {
         e.printStackTrace();
         return "delete failure";
      }
   }

}
