CREATE TABLE machine (
    id UUID primary key,
    version NUMERIC(20,0) not null,
    active BOOLEAN not null,
    name VARCHAR(255),
    created_at TIMESTAMPTZ not null,
    updated_at TIMESTAMPTZ not null
);