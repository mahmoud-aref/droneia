CREATE TABLE IF NOT EXISTS users
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    active    BOOLEAN          DEFAULT TRUE,
    role      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS drones
(
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    serial_number      VARCHAR(100),
    model              VARCHAR(255) NOT NULL,
    max_weight         DOUBLE PRECISION CHECK (maxWeight <= 500),
    battery_percentage INTEGER          DEFAULT 100 CHECK (batteryPercentage <= 100),
    state              VARCHAR(255)     DEFAULT 'IDLE'
);

CREATE TABLE IF NOT EXISTS medications
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name      VARCHAR(255) CHECK (name ~ '^[a-zA-Z0-9_-]+$'),
    weight    DOUBLE PRECISION,
    code      VARCHAR(255) CHECK (code ~ '^[A-Z0-9_]+$'),
    image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS orders
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    drone_id UUID,
    state              VARCHAR(255)     DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS order_medications
(
    order_id      UUID,
    medication_id UUID,
    PRIMARY KEY (order_id, medication_id)
);

ALTER TABLE orders
    ADD FOREIGN KEY (drone_id) REFERENCES drones (id);

ALTER TABLE order_medications
    ADD FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_medications
    ADD FOREIGN KEY (medication_id) REFERENCES medications (id);