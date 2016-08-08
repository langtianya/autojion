drop database if exists cloudclick;

create database cloudclick;

use cloudclick;

create table cc_account(
      aId BIGINT(18) auto_increment not null,
      aName varchar(500) NOT NULL,
      aPwd varchar(500) NOT NULL,
      aPhone varchar(100),
      aQq varchar(100),
      uId BIGINT(18),
      aStatus tinyint(1) DEFAULT NULL,
      aTotal varchar(9999),
      aLast varchar(9999),
      aDate date ,
      aIp varchar(100),
      online tinyint(1) DEFAULT NULL,
      aAgent int,
PRIMARY KEY  (aId)
) ENGINE=MyISAM  DEFAULT CHARSET=gbk;

  create table cc_user
  (
      uId int(18) auto_increment not null,
      uName varchar(500) not null,
      uPwd varchar(500) not null,
      uAgents varchar(100),
      uAuth    int ,
      uDate    date ,
      PRIMARY KEY  (uId)
  )ENGINE=MyISAM  DEFAULT CHARSET=gbk;



 create table cc_record(
     rId int(18)  auto_increment not null,
     aName varchar(500) NOT NULL,
     aId    int(18)           not null,
     rClick     varchar(500) ,
     rMoney varchar(500)  ,
     uName varchar(500) not null,
     uId         int(18)           not null,
     uuId         int(18),
     uAgent varchar(100),
     rDate timestamp,
     PRIMARY KEY  (rId)
 )  ENGINE=MyISAM  DEFAULT CHARSET=gbk;

create table cc_refresh(
    rId int (18)     auto_increment not null,
     rKeyword    varchar(500)  ,
     rUrl       varchar(500)  ,
     rSEType   varchar(500)  ,
     rIp     varchar(500)  ,
     rDate    date,
     tId    int(18)           not null,
       PRIMARY KEY  (rId)
)      ENGINE=MyISAM  DEFAULT CHARSET=gbk;

create table cc_task(
                             tId int (18)     auto_increment not null  ,

                             tKeyword varchar(500)  ,
                            tUrl varchar(500)  ,
                            tSEType varchar(500)  ,
                              tSetClick int,
                             tAssignedClick int ,
                             tHaveClick int,
--tPriority int,
                            tDate date,
                              activated tinyint(1),
                              aId int,
                         PRIMARY KEY  (tId)
)ENGINE=MyISAM  DEFAULT CHARSET=gbk;


alter table cc_record add constraint FK_Reference_2 foreign key (aId)
      references cc_account (aId) on delete restrict on update restrict;

alter table cc_record add constraint FK_Reference_3 foreign key (uId)
      references cc_user (uId) on delete restrict on update restrict;

alter table cc_refresh add constraint FK_Reference_4 foreign key (tId)
      references cc_task (tId) on delete restrict on update restrict;

alter table cc_task add constraint FK_Reference_1 foreign key (aId)
      references cc_account (aId) on delete restrict on update restrict;

alter table cc_account  add constraint FK_Reference_1 foreign key (aUserId) 
      references  cc_user (uId) on delete restrict on update restrict;
      
create  index  IDX_ACCOUNT_ID on cc_account(aId);
create  index  IDX_RECORD_ID on cc_record(rId);
create index IDX_REFRESH on  cc_refresh(rId) ;

ALTER TABLE cc_task ADD INDEX task (tKeyword)
ALTER TABLE cc_task ADD INDEX rUrl (tUrl)
ALTER TABLE cc_task ADD INDEX rSEType (tSEType)
ALTER TABLE cc_task ADD INDEX rId (aId)

ALTER TABLE cc_refresh ADD INDEX task (rKeyword)
ALTER TABLE cc_refresh ADD INDEX rUrl (rUrl)
ALTER TABLE cc_refresh ADD INDEX rSEType (rSEType)
ALTER TABLE cc_refresh ADD INDEX rId (rId)


ALTER TABLE cc_user ADD INDEX task (uName)
ALTER TABLE cc_account ADD INDEX aName (aName)
ALTER TABLE cc_account ADD INDEX aPhone (aPhone)
ALTER TABLE cc_account ADD INDEX rId (aQq)

ALTER TABLE cc_account ADD INDEX uId (uId)
ALTER TABLE cc_account ADD INDEX aDate (aDate)

insert into cc_user (uName,uPwd,uAgents,uAuth,uDate) values ('admin','test','huichuang',0,'20130228');
create  index  IDX_ACCOUNT_AName on cc_account(aName);
create  index  IDX_ACCOUNT_UAgent on cc_account(uAgents);
create  index  IDX_ACCOUNT_UAuth on cc_account(uAuth);
create  index  IDX_ACCOUNT_UDate on cc_account(uDate);

create  index  IDX_RECORD_UName on cc_record(uName);
create  index  IDX_RECORD_AName on cc_record(aName);
create  index  IDX_RECORD_UID on cc_record(uId);

create  index  IDX_RECORD_AId on cc_record(aId);
create  index  IDX_RECORD_UAgent on cc_record(uAgent);
create  index  IDX_RECORD_RDate on cc_record(rDate);

create  index  IDX_RECORD_RMoney on cc_record(rMoney);
create  index  IDX_RECORD_RClick on cc_record(rClick);

create  index  IDX_REFRESH_RDate on  cc_refresh(rDate) ;
create  index  IDX_REFRESH_RKeyword on  cc_refresh(rKeyword);
create  index  IDX_TASK_TKeyword on cc_task(tKeyword);

insert into cc_user (uName,uPwd,uAgents,uAuth,uDate) values ('xiaodai','xiaodai','1',0,'20130228');
insert into cc_user (uName,uPwd,uAgents,uAuth,uDate) values ('hulian','hulian','1',0,'20130228');
insert into cc_user (uName,uPwd,uAgents,uAuth,uDate) values ('xiaoou','xiaoou','1',0,'20130228');
