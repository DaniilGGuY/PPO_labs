create role guest_r;
create role client_r;
create role consultant_r;
create role manager_r;

grant select on review, consultant, product to guest_r;
grant select on all tables in schema public to client_r;
grant insert, update on client, orders, message, review to client_r;

grant select on all tables in schema public to consultant_r;
grant update on consultant, orders, message to consultant_r;
grant insert on consultant, message to consultant_r;

grant select on all tables in schema public to manager_r;
grant insert, update on manager, product to manager_r;
