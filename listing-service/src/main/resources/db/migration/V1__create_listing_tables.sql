-- ── listings ──────────────────────────────────────────────────────
CREATE TABLE listings
(
    id               BIGINT                                     NOT NULL AUTO_INCREMENT,
    vehicle_id       BIGINT                                     NOT NULL,
    seller_id        BIGINT                                     NOT NULL,
    price            DECIMAL(12, 2)                             NOT NULL,
    expected_price   DECIMAL(12, 2),
    final_sale_price DECIMAL(12, 2),
    is_negotiable    BOOLEAN                                    NOT NULL DEFAULT FALSE,
    description      TEXT,
    meeting_location VARCHAR(255),
    status           ENUM ('ACTIVE','SOLD','EXPIRED','REMOVED') NOT NULL DEFAULT 'ACTIVE',
    listed_at        TIMESTAMP                                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sold_at          TIMESTAMP,
    expires_at       TIMESTAMP,
    updated_at       TIMESTAMP                                  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_vehicle_id (vehicle_id),
    INDEX idx_seller_id (seller_id),
    INDEX idx_status (status)
);

-- ── listing_enquiries ─────────────────────────────────────────────
CREATE TABLE listing_enquiries
(
    id             BIGINT                                       NOT NULL AUTO_INCREMENT,
    listing_id     BIGINT                                       NOT NULL,
    buyer_id       BIGINT                                       NOT NULL,
    message        TEXT,
    contact_number VARCHAR(15),
    status         ENUM ('PENDING','SEEN','RESPONDED','CLOSED') NOT NULL DEFAULT 'PENDING',
    created_at     TIMESTAMP                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP                                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_enquiry_listing FOREIGN KEY (listing_id) REFERENCES listings (id),
    INDEX idx_buyer_id (buyer_id)
);

-- ── vehicle_expenses ──────────────────────────────────────────────
CREATE TABLE vehicle_expenses
(
    id           BIGINT                                                                         NOT NULL AUTO_INCREMENT,
    vehicle_id   BIGINT                                                                         NOT NULL,
    seller_id    BIGINT                                                                         NOT NULL,
    listing_id   BIGINT,
    expense_type ENUM ('PURCHASE','REPAIR','WASHING','FINE','INSURANCE','PLATFORM_FEE','OTHER') NOT NULL,
    amount       DECIMAL(12, 2)                                                                 NOT NULL,
    description  VARCHAR(255),
    incurred_at  DATE                                                                           NOT NULL,
    created_at   TIMESTAMP                                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_expense_listing FOREIGN KEY (listing_id) REFERENCES listings (id),
    INDEX idx_expense_vehicle_id (vehicle_id),
    INDEX idx_expense_seller_id (seller_id),
    INDEX idx_expense_type (expense_type),
    INDEX idx_incurred_at (incurred_at)
);