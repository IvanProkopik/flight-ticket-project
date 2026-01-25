-- Створення бази даних
CREATE DATABASE flight_repository;

-- Підключення до бази
\c flight_repository;

-- Таблиця рейсів
CREATE TABLE flight (
    id BIGSERIAL PRIMARY KEY,
    flight_no VARCHAR(16) NOT NULL,
    departure_date TIMESTAMP NOT NULL,
    departure_airport_code VARCHAR(3) NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    arrival_airport_code VARCHAR(3) NOT NULL,
    aircraft_id INTEGER NOT NULL,
    status VARCHAR(32) NOT NULL
);

-- Таблиця квитків
CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    passenger_no VARCHAR(32) NOT NULL,
    passenger_name VARCHAR(128) NOT NULL,
    flight_id BIGINT NOT NULL,
    seat_no VARCHAR(4) NOT NULL,
    cost NUMERIC(8, 2) NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight(id) ON DELETE CASCADE
);

-- Індекси для оптимізації
CREATE INDEX idx_ticket_flight_id ON ticket(flight_id);
CREATE INDEX idx_flight_departure ON flight(departure_date);
CREATE INDEX idx_flight_status ON flight(status);