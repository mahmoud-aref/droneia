CREATE TABLE users
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    role VARCHAR(255)
);

CREATE TABLE drones
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    serial_number VARCHAR(100),
    model VARCHAR(255) NOT NULL,
    max_weight DOUBLE PRECISION CHECK (maxWeight <= 500),
    battery_percentage INTEGER DEFAULT 100 CHECK (batteryPercentage <= 100),
    state VARCHAR(255) DEFAULT 'IDLE'
);

CREATE TABLE medications
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) CHECK (name ~ '^[a-zA-Z0-9_-]+$'),
    weight DOUBLE PRECISION,
    code VARCHAR(255) CHECK (code ~ '^[A-Z0-9_]+$'),
    imageUrl VARCHAR(255)
);

