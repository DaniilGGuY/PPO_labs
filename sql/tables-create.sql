create table if not exists public.client (
    id serial primary key,
	login text unique not null,
	password text not null check (length(password) >= 8),
    tel text check (tel ~ '^\+?[0-9\s\-\(\)]+$') not null,
    email text not null check (email ~ '^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$'),
    registration timestamp not null default current_timestamp,
    fio text,
    company text not null,
    about text check (length(about) < 1000)
);

create table if not exists public.consultant (
    id serial primary key,
	login text unique not null,
	password text not null check (length(password) >= 8),
    tel text check (tel ~ '^\+?[0-9\s\-\(\)]+$') not null,
    email text not null check (email ~ '^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$'),
    registration timestamp not null default current_timestamp,
    fio text not null,
    specialization text not null,
    age integer not null check (age >= 18 and age <= 80),
    experience integer not null check (experience >= 0 and experience <= age - 18),
    rating float check (rating >= 1ys and rating <= 5),
    about text check (length(about) < 1000)
);

create table if not exists public.manager (
	id serial primary key,
	login text unique not null,
	password text not null check (length(password) >= 8),
	tel text check (tel ~ '^\+?[0-9\s\-\(\)]+$') not null,
    email text not null check (email ~ '^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,}$'),
	registration timestamp not null default current_timestamp,
	fio text not null,
	about text check (length(about) < 1000)
);

create table if not exists public.product (
	id serial primary key,
	cost float not null check (cost > 0),
	name text not null check (length(name) < 200),
	about text check (length(about) < 1000)
);

create table if not exists public.orders (
	id serial primary key, 
	cost float not null check (cost >= 0),
	datetime timestamp not null default current_timestamp,
	client_id integer not null references public.client(id),
	consultant_id integer references public.consultant(id),
	status text not null default 'PENDING' check (status in ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')) 
);

create table if not exists public.message (
	id serial primary key, 
	datetime timestamp not null default current_timestamp,
	text text not null,
	client_id integer not null references public.client(id),
	consultant_id integer not null references public.consultant(id),
	is_client bool not null
);

create table if not exists public.review (
	id serial primary key, 
	rating text check (rating in ('AWFUL', 'BAD', 'NORMAL', 'GOOD', 'AWESOME')),
	datetime timestamp not null default current_timestamp,
	text text not null,
	client_id integer not null references public.client(id),
	consultant_id integer not null references public.consultant(id)
);

create table if not exists public.orderproduct (
	order_id integer not null references public.orders(id),
    product_id integer not null references public.product(id),
    quantity integer not null check (quantity > 0)
);

