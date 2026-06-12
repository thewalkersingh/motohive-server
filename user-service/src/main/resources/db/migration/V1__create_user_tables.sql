-- ── users ─────────────────────────────────────────────────────────
CREATE TABLE users
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    first_name           VARCHAR(50)  NOT NULL,
    last_name            VARCHAR(50)  NOT NULL,
    email                VARCHAR(150) NOT NULL,
    password             VARCHAR(255) NOT NULL,
    phone                VARCHAR(15),
    profile_photo_url    VARCHAR(500),
    gender               ENUM ('MALE','FEMALE','OTHER'),
    date_of_birth        DATE,
    status               ENUM ('ACTIVE','INACTIVE','SUSPENDED','PENDING_VERIFICATION')
                                      NOT NULL DEFAULT 'ACTIVE',
    refresh_token        VARCHAR(500),
    refresh_token_expiry TIMESTAMP,
    created_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_email (email),
    UNIQUE KEY uq_phone (phone)
);

-- ── user_roles ────────────────────────────────────────────────────
CREATE TABLE user_roles
(
    user_id BIGINT                          NOT NULL,
    role    ENUM ('BUYER','SELLER','ADMIN') NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_role_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- ── user_addresses ────────────────────────────────────────────────
CREATE TABLE user_addresses
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    address_line VARCHAR(255) NOT NULL,
    city         VARCHAR(100) NOT NULL,
    state        VARCHAR(100) NOT NULL,
    pincode      VARCHAR(10)  NOT NULL,
    is_default   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES users (id),
    INDEX idx_address_user_id (user_id)
);

-- ── vehicle_appointments ──────────────────────────────────────────
CREATE TABLE vehicle_appointments
(
    id               BIGINT                            NOT NULL AUTO_INCREMENT,
    vehicle_id       BIGINT                            NOT NULL,
    listing_id       BIGINT                            NOT NULL,
    user_id          BIGINT,
    guest_name       VARCHAR(100),
    guest_phone      VARCHAR(15),
    appointment_type ENUM ('TEST_RIDE','VIEW_VEHICLE') NOT NULL,
    preferred_date   DATE                              NOT NULL,
    preferred_time   TIME                              NOT NULL,
    notes            TEXT,
    status           ENUM ('PENDING','CONFIRMED','COMPLETED','CANCELLED')
                                                       NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_appointment_user FOREIGN KEY (user_id) REFERENCES users (id),
    INDEX idx_appointment_vehicle_id (vehicle_id),
    INDEX idx_appointment_listing_id (listing_id),
    INDEX idx_appointment_user_id (user_id)
);