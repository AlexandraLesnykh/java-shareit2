CREATE table IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name varchar (20) NOT NULL,
    email varchar (20) NOT NULL CHECK (email LIKE '%@%.%'),
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE(email)
    );


CREATE table IF NOT EXISTS item_request(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description varchar (256) NOT NULL,
    requestor BIGINT REFERENCES users (id) ON DELETE RESTRICT,
    created TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_item_request PRIMARY KEY (id)
    );

CREATE table IF NOT EXISTS items(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar (40) NOT NULL,
    description varchar (256) NOT NULL,
    available boolean NOT NULL,
    owner BIGINT REFERENCES users (id) ON DELETE RESTRICT,
    request_id BIGINT REFERENCES item_request (id) ON DELETE RESTRICT
);

CREATE table IF NOT EXISTS bookings(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    booking_start TIMESTAMP WITHOUT TIME ZONE,
    booking_end TIMESTAMP WITHOUT TIME ZONE CHECK (booking_start <= bookings.booking_end),
    item_id BIGINT REFERENCES items (id) ON DELETE RESTRICT,
    booker_id BIGINT REFERENCES users (id) ON DELETE RESTRICT,
    booking_status varchar,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE table IF NOT EXISTS comments(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    comment_text varchar (150) NOT NULL,
    item_id BIGINT REFERENCES items (id) ON DELETE RESTRICT,
    author_id BIGINT REFERENCES users (id) ON DELETE RESTRICT,
    created TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_comment PRIMARY KEY (id)
    );
