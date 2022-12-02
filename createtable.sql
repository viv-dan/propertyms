drop database IF EXISTS propertyproject;
create database if not exists propertyproject;
use propertyproject;

-- Company Table
CREATE TABLE management_company(
 cid INT auto_increment PRIMARY KEY,
 company_name VARCHAR(50) NOT NULL,
 email_id VARCHAR(100) NOT NULL,
 phone_number CHAR(10) NOT NULL);
 
 
 -- Building Table
 CREATE TABLE building(
 bid INT auto_increment PRIMARY KEY,
 cid INT NOT NULL,
 address VARCHAR(600) NOT NULL,
 building_name VARCHAR(100) NOT NULL,
 zipcode VARCHAR(10) NOT NULL,
 number_of_floors INT NOT NULL,
 parking_spots INT NOT NULL,
 type_of_building VARCHAR(10),
 CONSTRAINT company_manages_building FOREIGN KEY (cid) REFERENCES management_company(cid) 
	ON UPDATE CASCADE ON DELETE RESTRICT);


-- Amenities Table

CREATE TABLE amenity(
aid int not null unique,
description varchar(100) not null);


-- Units in a Building Table
CREATE TABLE unit(
unit_no INT NOT NULL,
bid INT NOT NULL,
no_of_bedrooms INT NOT NULL,
no_of_bathrooms INT NOT NULL,
area DECIMAL(5,2) NOT NULL,
price  DECIMAL(10,2),
CONSTRAINT PRIMARY KEY (unit_no,bid),
CONSTRAINT building_has_unit FOREIGN KEY (bid) REFERENCES building(bid) 
	ON UPDATE CASCADE ON DELETE RESTRICT);
    
-- Lease Table
CREATE TABLE lease(
lid int PRIMARY KEY,
start_date DATE NOT NULL,
end_date DATE NOT NULL);


-- Tenant Table
CREATE TABLE tenant(
tid INT AUTO_INCREMENT PRIMARY KEY,
tname VARCHAR(50) NOT NULL,
date_of_birth DATE,
occupation VARCHAR(10),
phone_number VARCHAR(10) NOT NULL);


-- Maintenance Personnel
CREATE TABLE m_personnel(
mid INT PRIMARY KEY,
mname VARCHAR(50) NOT NULL,
bid INT NOT NULL,
phone_number VARCHAR(10) NOT NULL,
type_of_personnel varchar(10) NOT NULL,
CONSTRAINT building_has_personnel FOREIGN KEY (bid) REFERENCES building(bid)
	ON UPDATE CASCADE ON DELETE CASCADE);


-- Building have amenities table
CREATE TABLE building_has_amenity(
bid int NOT NULL,
aid int NOT NULL,
CONSTRAINT b_with_table FOREIGN KEY (bid) REFERENCES building(bid)
	ON UPDATE CASCADE ON DELETE RESTRICT,
CONSTRAINT a_with_table FOREIGN KEY (aid) REFERENCES amenity(aid)
	ON UPDATE CASCADE ON DELETE RESTRICT);
    
    
-- Units Leased to Tenants
CREATE TABLE isleased(
lid int not null,
unit_no int not null,
tid int not null,
CONSTRAINT lease_with_table FOREIGN KEY (lid) REFERENCES lease(lid)
	ON UPDATE CASCADE ON DELETE RESTRICT,
CONSTRAINT unit_with_table FOREIGN KEY (unit_no) REFERENCES unit(unit_no)
	ON UPDATE CASCADE ON DELETE RESTRICT,
CONSTRAINT tenant_with_table FOREIGN KEY (tid) REFERENCES tenant(tid)
	ON UPDATE CASCADE ON DELETE RESTRICT );   


-- Tenants Request for maintenance 
CREATE TABLE request(
unit_no int not null,
tid int not null,
mid INT not null,
r_description VARCHAR(50) not null,
r_status BOOLEAN not null DEFAULT FALSE,
CONSTRAINT M_with_table FOREIGN KEY (mid) REFERENCES m_personnel(mid)
	ON UPDATE CASCADE ON DELETE RESTRICT,
CONSTRAINT u_table FOREIGN KEY (unit_no) REFERENCES unit(unit_no)
	ON UPDATE CASCADE ON DELETE RESTRICT,
CONSTRAINT t_with_table FOREIGN KEY (tid) REFERENCES tenant(tid)
	ON UPDATE CASCADE ON DELETE RESTRICT );


    















