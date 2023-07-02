drop table if exists voucher;
create table voucher (
         voucher_id uuid primary key,
         voucher_type varchar(20) not null,
         amount int not null
);

drop table if exists customer;
create table customer (
          customer_id uuid primary key,
          email varchar(50) not null,
          name varchar(20) not null
)