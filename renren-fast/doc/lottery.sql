use
renren
go

create table activity
(
    activity_id                bigint        not null
        constraint activity_pk
            primary key,
    activity_name              varchar(3000) not null,
    activity_start_time        datetime      not null,
    activity_end_time          datetime      not null,
    participation_limit        int default 0,
    participation_number       int,
    participation_times        int default 1 not null,
    participation_time_refresh int default 0 not null,
    participation_cost_type    int default 1 not null,
    participation_cost         decimal       not null,
    win_news_limit             int default 0 not null,
    activity_status            int default 0 not null,
    strategy_type              int default 0 not null,
    win_prize_number           int,
    batch_get                  int default 0,
    batch_get_number           int default 0 not null,
    default_prize_id           bigint        not null
) go

exec sp_addextendedproperty 'MS_Description', '活动id', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN', 'activity_id'
go

exec sp_addextendedproperty 'MS_Description', '活动名称', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN', 'activity_name'
go

exec sp_addextendedproperty 'MS_Description', '活动开始日期', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'activity_start_time'
go

exec sp_addextendedproperty 'MS_Description', '活动结束日期', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'activity_end_time'
go

exec sp_addextendedproperty 'MS_Description', '参与人数限制 0 不限制 1 限制', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'participation_limit'
go

exec sp_addextendedproperty 'MS_Description', '参与人数', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'participation_number'
go

exec sp_addextendedproperty 'MS_Description', '每个用户参与次数', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'participation_times'
go

exec sp_addextendedproperty 'MS_Description', '每个用户参与次数刷新规则 0 不刷新 1 日 2 月', 'SCHEMA', 'dbo', 'TABLE', 'activity',
     'COLUMN', 'participation_time_refresh'
go

exec sp_addextendedproperty 'MS_Description', '用户参与成本类型 0 无成本 1 积分', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'participation_cost_type'
go

exec sp_addextendedproperty 'MS_Description', '用户参与成本', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'participation_cost'
go

exec sp_addextendedproperty 'MS_Description', '大奖通告限制 0 不限制 1 限制', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'win_news_limit'
go

exec sp_addextendedproperty 'MS_Description', '活动状态 0 开启 1 已关闭', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'activity_status'
go

exec sp_addextendedproperty 'MS_Description', '0 普通 1 保底 2 动态', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'strategy_type'
go

exec sp_addextendedproperty 'MS_Description', '保底中奖次数', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'win_prize_number'
go

exec sp_addextendedproperty 'MS_Description', '连抽 0 否 1是', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN', 'batch_get'
go

exec sp_addextendedproperty 'MS_Description', '最大连抽次数', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN',
     'batch_get_number'
go

exec sp_addextendedproperty 'MS_Description', '兜底奖品', 'SCHEMA', 'dbo', 'TABLE', 'activity', 'COLUMN', 'default_prize_id'
go


use renren
go

create table activity_prize
(
    activity_id       bigint        not null,
    prize_id          bigint        not null,
    prize_number      int default 1 not null,
    prize_probability decimal(18, 8),
    prize_total_limit int default 0,
    prize_total       bigint,
    prize_stock       bigint
) go

exec sp_addextendedproperty 'MS_Description', '活动 id', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'activity_id'
go

exec sp_addextendedproperty 'MS_Description', '奖品id', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN', 'prize_id'
go

exec sp_addextendedproperty 'MS_Description', '奖品数量', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'prize_number'
go

exec sp_addextendedproperty 'MS_Description', '奖品概率', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'prize_probability'
go

exec sp_addextendedproperty 'MS_Description', '奖品总数限制 0 不限制 1限制', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'prize_total_limit'
go

exec sp_addextendedproperty 'MS_Description', '奖品总数', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'prize_total'
go

exec sp_addextendedproperty 'MS_Description', '奖品库存', 'SCHEMA', 'dbo', 'TABLE', 'activity_prize', 'COLUMN',
     'prize_stock'
go

use renren
go

create table crm_client
(
    ID                    bigint       not null
        constraint PK_qm_client
            primary key,
    ClientId              varchar(50)  not null,
    ClientSecret          varchar(256) not null,
    ResourceIds           varchar(256),
    Scope                 varchar(100) not null,
    AuthorizedGrantTypes  varchar(256) not null,
    WebServerRedirectURI  varchar(256),
    Authorities           varchar(256),
    AccessTokenValidity   int          not null,
    RefreshTokenValidity  int          not null,
    AdditionalInformation varchar(4096),
    AutoApprove           varchar(256),
    CreateTime            datetime
        constraint DF_qm_client_CreateTime default getdate(),
    UpdateTime            datetime
        constraint DF_qm_client_UpdateTime default getdate(),
    Status                tinyint
        constraint DF_qm_client_Status default 0 not null,
    IsDeleted             tinyint
        constraint DF_qm_client_IsDeleted default 0 not null
) go

exec sp_addextendedproperty 'MS_Description', '认证客户端表', 'SCHEMA', 'dbo', 'TABLE', 'crm_client'
go

exec sp_addextendedproperty 'MS_Description', '主键', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ID'
go

exec sp_addextendedproperty 'MS_Description', '客户端ID', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ClientId'
go

exec sp_addextendedproperty 'MS_Description', '客户端密钥', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ClientSecret'
go

exec sp_addextendedproperty 'MS_Description', '资源集合', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ResourceIds'
go

exec sp_addextendedproperty 'MS_Description', '授权范围', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'Scope'
go

exec sp_addextendedproperty 'MS_Description', '授权类型', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AuthorizedGrantTypes'
go

exec sp_addextendedproperty 'MS_Description', '回调地址', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'WebServerRedirectURI'
go

exec sp_addextendedproperty 'MS_Description', '权限', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'Authorities'
go

exec sp_addextendedproperty 'MS_Description', '令牌过期秒数', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AccessTokenValidity'
go

exec sp_addextendedproperty 'MS_Description', '刷新令牌过期秒数', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'RefreshTokenValidity'
go

exec sp_addextendedproperty 'MS_Description', '附加说明', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AdditionalInformation'
go

exec sp_addextendedproperty 'MS_Description', '自动授权', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'AutoApprove'
go

exec sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'CreateTime'
go

exec sp_addextendedproperty 'MS_Description', '更新时间', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'UpdateTime'
go

exec sp_addextendedproperty 'MS_Description', '业务状态，0正常，1不正常', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'Status'
go

exec sp_addextendedproperty 'MS_Description', '删除状态，0未删除，1已删除', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'IsDeleted'
go

use renren
go

create table customer
(
    c_id       bigint        not null
        constraint customer_pk
            primary key,
    c_name     varchar(300)  not null,
    c_phone    varchar(30)   not null,
    c_password varchar(3000) not null
) go

use renren
go

create table customer_activity_detail
(
    c_id        bigint   not null,
    activity_id bigint   not null,
    prize_id    bigint   not null,
    join_time   datetime not null
) go

exec sp_addextendedproperty 'MS_Description', '客户id', 'SCHEMA', 'dbo', 'TABLE', 'customer_activity_detail', 'COLUMN',
     'c_id'
go

exec sp_addextendedproperty 'MS_Description', '活动id', 'SCHEMA', 'dbo', 'TABLE', 'customer_activity_detail', 'COLUMN',
     'activity_id'
go

exec sp_addextendedproperty 'MS_Description', '奖品id', 'SCHEMA', 'dbo', 'TABLE', 'customer_activity_detail', 'COLUMN',
     'prize_id'
go

exec sp_addextendedproperty 'MS_Description', '参加时间', 'SCHEMA', 'dbo', 'TABLE', 'customer_activity_detail', 'COLUMN',
     'join_time'
go

use renren
go

create table customer_integral
(
    c_id     bigint        not null,
    integral int default 0 not null
) go

use renren
go

create table prize
(
    prize_id   bigint        not null
        constraint prize_pk
            primary key,
    prize_name varchar(3000) not null
)
    go



use renren
go

create table crm_client
(
    ID                    bigint                    not null
        constraint PK_qm_client
            primary key,
    ClientId              varchar(50)               not null,
    ClientSecret          varchar(256)              not null,
    ResourceIds           varchar(256),
    Scope                 varchar(100)              not null,
    AuthorizedGrantTypes  varchar(256)              not null,
    WebServerRedirectURI  varchar(256),
    Authorities           varchar(256),
    AccessTokenValidity   int                       not null,
    RefreshTokenValidity  int                       not null,
    AdditionalInformation varchar(4096),
    AutoApprove           varchar(256),
    CreateTime            datetime
        constraint DF_qm_client_CreateTime default getdate(),
    UpdateTime            datetime
        constraint DF_qm_client_UpdateTime default getdate(),
    Status                tinyint
        constraint DF_qm_client_Status default 0    not null,
    IsDeleted             tinyint
        constraint DF_qm_client_IsDeleted default 0 not null
)
    go

exec sp_addextendedproperty 'MS_Description', '认证客户端表', 'SCHEMA', 'dbo', 'TABLE', 'crm_client'
go

exec sp_addextendedproperty 'MS_Description', '主键', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ID'
go

exec sp_addextendedproperty 'MS_Description', '客户端ID', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ClientId'
go

exec sp_addextendedproperty 'MS_Description', '客户端密钥', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ClientSecret'
go

exec sp_addextendedproperty 'MS_Description', '资源集合', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'ResourceIds'
go

exec sp_addextendedproperty 'MS_Description', '授权范围', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'Scope'
go

exec sp_addextendedproperty 'MS_Description', '授权类型', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AuthorizedGrantTypes'
go

exec sp_addextendedproperty 'MS_Description', '回调地址', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'WebServerRedirectURI'
go

exec sp_addextendedproperty 'MS_Description', '权限', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'Authorities'
go

exec sp_addextendedproperty 'MS_Description', '令牌过期秒数', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AccessTokenValidity'
go

exec sp_addextendedproperty 'MS_Description', '刷新令牌过期秒数', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'RefreshTokenValidity'
go

exec sp_addextendedproperty 'MS_Description', '附加说明', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'AdditionalInformation'
go

exec sp_addextendedproperty 'MS_Description', '自动授权', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'AutoApprove'
go

exec sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'CreateTime'
go

exec sp_addextendedproperty 'MS_Description', '更新时间', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN', 'UpdateTime'
go

exec sp_addextendedproperty 'MS_Description', '业务状态，0正常，1不正常', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'Status'
go

exec sp_addextendedproperty 'MS_Description', '删除状态，0未删除，1已删除', 'SCHEMA', 'dbo', 'TABLE', 'crm_client', 'COLUMN',
     'IsDeleted'
go


INSERT INTO renren.dbo.crm_client (ID, ClientId, ClientSecret, ResourceIds, Scope, AuthorizedGrantTypes, WebServerRedirectURI, Authorities, AccessTokenValidity, RefreshTokenValidity, AdditionalInformation, AutoApprove, CreateTime, UpdateTime, Status, IsDeleted) VALUES (1123598811738675201, N'luck', N'luck_secret', null, N'all', N'refresh_token,password,authorization_code,captcha', N'http://localhost:8080', null, 3600, 604800, null, null, N'2022-03-30 13:03:55.470', N'2022-03-30 13:03:55.470', 0, 0);



INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (0, N'默认的奖品啥也不是');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508340681737764865, N'macBook');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508340770896084993, N'iphone');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508340886117810178, N'10元红包');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508340927012274177, N'xx优惠券');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508340977314562050, N'xx体验卡');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508341016300617729, N'30积分');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508341055295062017, N'20积分');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508341076090421250, N'10积分');
INSERT INTO renren.dbo.prize (prize_id, prize_name) VALUES (1508341140846280705, N'谢谢参与');
