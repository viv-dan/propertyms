use propertyproject;

DELIMITER //
create PROCEDURE get_all_buildings()
BEGIN
SELECT building_name from building;
END//
DELIMITER ;


DELIMITER //
create PROCEDURE get_maintenance_requests(IN  buildingname VARCHAR(200), IN unitno INT)
BEGIN
DECLARE buildingid INT;
SELECT bid into buildingid from building where building_name = buildingname;
SELECT r_description as description, r_status as status from request r where r.unit_no = unitno and r.building_id = buildingid;
END//

DELIMITER ;

DELIMITER //

create function validate_tenant(tenant_name VARCHAR(100), tenant_password VARCHAR(100))
RETURNS INT
DETERMINISTIC READS SQL DATA
BEGIN
DECLARE tenant_id INT;
SELECT tid into tenant_id from tenant t where t.tname = tenant_name and t.tpassword = tenant_password;
RETURN(tenant_id);
END //
DELIMITER ;


DELIMITER //

create function validate_company(companyname VARCHAR(100), company_password VARCHAR(100))
RETURNS INT
DETERMINISTIC READS SQL DATA
BEGIN
DECLARE company_id INT;
SELECT cid into company_id from management_company c where c.company_name = companyname and c.cpassword = company_password;
RETURN(company_id);
END //
DELIMITER ;


DELIMITER //

create function get_tenants(buildingname VARCHAR(100), unitno INT)
RETURNS VARCHAR(50)
DETERMINISTIC READS SQL DATA
BEGIN
DECLARE tenantname VARCHAR(50);
DECLARE tenantid INT;
SELECT tid INTO tenantid from isleased il left join building b on il.bid = b.bid where il.unit_no = unitno and b.building_name = buildingname;
SELECT tname INTO tenantname from tenant t where t.tid = tenantid;
RETURN(tenantname);
END //
DELIMITER ;

DELIMITER //

create function is_unit_available(buildingname VARCHAR(100), unitno INT)
RETURNS BOOLEAN
DETERMINISTIC READS SQL DATA
BEGIN
DECLARE leaseid INT;
DECLARE endDate DATE;
SELECT lid INTO leaseid from isleased il left join building b on il.bid = b.bid where b.building_name = buildingname and il.unit_no = unitno;
if leaseid is NULL THEN
RETURN TRUE;
END IF;
SELECT end_date into endDate from lease l where l.lid = leaseid;
if endDate is NULL THEN
RETURN TRUE;
END IF;
if endDate<(select CURDATE()) THEN
RETURN TRUE;
ELSE 
RETURN FALSE;
END IF;
END //
DELIMITER ;


DELIMITER //

create procedure get_building_amenities(IN buildingname VARCHAR(100))
BEGIN
DECLARE buildingid INT;
SELECT bid into buildingid from building where building_name = buildingname;
select GROUP_CONCAT(description) as amenities from building_has_amenity bha join amenity on bha.aid = amenity.aid where bha.bid = buildingid 
group by bha.bid;
END //
DELIMITER ;

DELIMITER //

create procedure get_building_available_units(IN buildingname VARCHAR(100))
BEGIN
DECLARE buildingid INT;
SELECT bid into buildingid from building where building_name = buildingname;
select GROUP_CONCAT(unit_no) as units from unit u where u.bid = buildingid and (select is_unit_available(buildingname, unit_no)) group by u.bid;
END //

DELIMITER ;

DELIMITER //
create procedure create_maintenance_requests(IN buildingname VARCHAR(100), IN unitno INT, IN description VARCHAR(200))
BEGIN
DECLARE mpersonnelid INT;
DECLARE buildingid INT;
select bid INTO buildingid from building b where b.building_name = buildingname; 
SELECT mid INTO mpersonnelid from m_personnel LIMIT 1;
INSERT INTO request (building_id, unit_no, mid, r_description) values (buildingid, unitno, mpersonnelid, description);
END //

DELIMITER ;


DELIMITER //
create procedure add_maintenance_personnel(IN buildingname VARCHAR(100), IN personnel_name VARCHAR(100), IN phone_num VARCHAR(10))
BEGIN
DECLARE buildingid INT;
select bid INTO buildingid from building b where b.building_name = buildingname;
INSERT INTO m_personnel (bid, mname, phone_number) values (buildingid, personnel_name, phone_num);
END //
DELIMITER ;

DELIMITER //

create procedure get_maintenance_personnel(IN buildingname VARCHAR(100))
BEGIN
DECLARE buildingid INT;
select bid INTO buildingid from building b where b.building_name = buildingname;
SELECT mname, phone_number from m_personnel where bid = buildingid;
END //
DELIMITER ;

DELIMITER //

create procedure get_lease_info(IN tenantname VARCHAR(100))
BEGIN
DECLARE tenantid INT;
select tid INTO tenantid from tenant t where t.tname = tenantname;
select il.unit_no, l.start_date, l.end_date from isleased il left join lease l on il.lid = l.lid where il.tid = tenantid;
END//

DELIMITER ;

DELIMITER //

create procedure add_company(IN cname VARCHAR(100), IN company_password VARCHAR(100), IN email VARCHAR(100), IN ph_no VARCHAR(100))
BEGIN
INSERT INTO management_company (company_name, cpassword, email_id, phone_number) VALUES (cname, company_password, email, ph_no);
END //
DELIMITER ;

DELIMITER //

create procedure add_tenant_to_unit(IN tenant_name VARCHAR(100), IN buildingname VARCHAR(100), IN unitno INT, IN tenantpassword VARCHAR(100), IN dob DATE, IN job VARCHAR(50), IN phno VARCHAR(10))
BEGIN
DECLARE tenantid INT;
DECLARE leaseid INT;
DECLARE buildingid INT;
DECLARE currdate DATE;
SELECT CURDATE() INTO currdate;
select bid INTO buildingid from building b where b.building_name = buildingname;
INSERT INTO tenant (tname, tpassword, date_of_birth, occupation, phone_number) values (tenant_name, tenantpassword, dob, job, phno);
SELECT tid INTO tenantid from tenant t where t.tname = tenant_name;
INSERT INTO lease (start_date, end_date) values ((currdate), (SELECT DATE_ADD(currdate,INTERVAL 1 year)));
SELECT lid INTO leaseid from lease where lid  = (SELECT max(lid) from lease);
INSERT INTO isleased (lid, unit_no, bid, tid) VALUES (leaseid, unitno, buildingid, tenantid);
END //

DELIMITER ;

DELIMITER //

create procedure add_unit(IN buildingname VARCHAR(100), IN noofbedrooms INT, IN noofbathrooms INT, IN unit_price DOUBLE, IN unit_area DOUBLE, IN unitNo INT)
BEGIN
DECLARE buildingid INT;
select bid INTO buildingid from building b where b.building_name = buildingname;
INSERT INTO unit (unit_no, bid, no_of_bedrooms, no_of_bathrooms, price, area) VALUES ( unitNo, buildingid, noofbedrooms, noofbathrooms, unit_price, unit_area);
END //

DELIMITER ;

DELIMITER //
create procedure get_amenities()
BEGIN
select description from amenity;
END //

DELIMITER ;


DELIMITER //
create procedure add_amenity(IN buildingname VARCHAR(100), IN amenity VARCHAR(100))
BEGIN
DECLARE buildingid INT;
DECLARE amenityid INT;
select bid INTO buildingid from building b where b.building_name = buildingname;
SELECT aid INTO amenityid from amenity where description = amenity;
INSERT INTO building_has_amenity(aid, bid) values (amenityid, buildingid);
end //

DELIMITER ;

insert into amenity (description) values ('heat');
insert into amenity (description) values ('hot water');
insert into amenity (description) values ('gym');
insert into amenity (description) values ('swimming pool');
insert into amenity (description) values ('trash');

DELIMITER //

create procedure create_building(IN companyname VARCHAR(100), IN addr VARCHAR(600), IN buildingname VARCHAR(100), IN zip_code VARCHAR(10), IN nooffloors INT, IN noofparkingspots INT, IN type VARCHAR(10))
BEGIN
DECLARE companyid INT;
SELECT cid INTO companyid from management_company where company_name = companyname;
INSERT INTO building (building_name, zipcode, cid, address, number_of_floors, parking_spots, type_of_building) VALUES (buildingname, zip_code, companyid, addr, nooffloors, noofparkingspots, type);
END //

DELIMITER ;

DELIMITER //
create procedure get_company_buildings(IN companyName VARCHAR(100))
BEGIN
DECLARE companyid INT;
SELECT cid INTO companyid from management_company where company_name = companyname;
select building_name from building where cid = companyid;
END //

DELIMITER ;















