-- ── vehicles ──────────────────────────────────────────────────────
CREATE TABLE vehicles
(
    id                  BIGINT                                             NOT NULL AUTO_INCREMENT,
    seller_id           BIGINT                                             NOT NULL,
    vehicle_type        ENUM ('CAR','BIKE','EV')                           NOT NULL,
    brand               VARCHAR(100)                                       NOT NULL,
    model               VARCHAR(100)                                       NOT NULL,
    year                SMALLINT                                           NOT NULL,
    fuel_type           ENUM ('PETROL','DIESEL','CNG','ELECTRIC','HYBRID') NOT NULL,
    transmission        ENUM ('MANUAL','AUTOMATIC')                        NOT NULL,
    mileage_kmpl        DECIMAL(5, 2),
    odometer_km         INT,
    color               VARCHAR(50),
    vehicle_condition   ENUM ('EXCELLENT','GOOD','FAIR','POOR')            NOT NULL,
    registration_number VARCHAR(20)                                        NOT NULL,
    insurance_expiry    DATE,
    number_of_owners    TINYINT                                            NOT NULL,
    city                VARCHAR(100)                                       NOT NULL,
    status              ENUM ('ACTIVE','SOLD','HOLD','BOOKED')             NOT NULL DEFAULT 'ACTIVE',
    created_at          TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP                                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_registration_number (registration_number)
);

-- ── car_details ───────────────────────────────────────────────────
CREATE TABLE car_details
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    vehicle_id        BIGINT NOT NULL,
    engine_cc         INT,
    seating_capacity  TINYINT,
    boot_space_litres INT,
    airbags           TINYINT,
    drive_type        ENUM ('FWD','RWD','AWD'),
    sunroof           BOOLEAN,
    abs               BOOLEAN,
    PRIMARY KEY (id),
    UNIQUE KEY uq_car_vehicle_id (vehicle_id),
    CONSTRAINT fk_car_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

-- ── bike_details ──────────────────────────────────────────────────
CREATE TABLE bike_details
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    engine_cc  INT,
    bike_type  ENUM ('SPORT','CRUISER','COMMUTER','SCOOTER','ELECTRIC', 'ADVENTURE'),
    abs        BOOLEAN,
    tyre_type  ENUM ('TUBELESS','TUBE'),
    PRIMARY KEY (id),
    UNIQUE KEY uq_bike_vehicle_id (vehicle_id),
    CONSTRAINT fk_bike_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

-- ── ev_details ────────────────────────────────────────────────────
CREATE TABLE ev_details
(
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    vehicle_id           BIGINT NOT NULL,
    battery_capacity_kwh DECIMAL(5, 2),
    range_km             INT,
    charge_time_hours    DECIMAL(4, 2),
    connector_type       ENUM ('CCS2','CHADEMO','TYPE2','GB_T'),
    top_speed_kmph       INT,
    drive_type           ENUM ('FWD','RWD','AWD'),
    PRIMARY KEY (id),
    UNIQUE KEY uq_ev_vehicle_id (vehicle_id),
    CONSTRAINT fk_ev_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

-- ── vehicle_images ────────────────────────────────────────────────
CREATE TABLE vehicle_images
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    vehicle_id    BIGINT       NOT NULL,
    image_url     VARCHAR(500) NOT NULL,
    is_primary    BOOLEAN      NOT NULL DEFAULT FALSE,
    display_order TINYINT,
    uploaded_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_image_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);