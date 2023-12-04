-- -- Create Rooms Table
-- CREATE TABLE Rooms (
--     room_id INT AUTO_INCREMENT PRIMARY KEY,
--     room_type VARCHAR(255) NOT NULL,
--     rate DECIMAL(10, 2) NOT NULL,
--     is_available BOOLEAN NOT NULL DEFAULT TRUE
-- );

-- -- Create Guests Table
-- CREATE TABLE Guests (
--     guest_id INT AUTO_INCREMENT PRIMARY KEY,
--     first_name VARCHAR(255) NOT NULL,
--     last_name VARCHAR(255) NOT NULL,
--     address TEXT,
--     phone VARCHAR(20),
--     email VARCHAR(255) UNIQUE
-- );

-- -- Create Reservations Table
-- CREATE TABLE Reservations (
--     reservation_id INT AUTO_INCREMENT PRIMARY KEY,
--     guest_id INT NOT NULL,
--     room_id INT NOT NULL,
--     check_in_date DATE NOT NULL,
--     check_out_date DATE NOT NULL,
--     FOREIGN KEY (guest_id) REFERENCES Guests(guest_id),
--     FOREIGN KEY (room_id) REFERENCES Rooms(room_id)
-- );

-- -- Create Billing Table
-- CREATE TABLE Billing (
--     bill_id INT AUTO_INCREMENT PRIMARY KEY,
--     reservation_id INT NOT NULL,
--     amount DECIMAL(10, 2) NOT NULL,
--     discount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
--     FOREIGN KEY (reservation_id) REFERENCES Reservations(reservation_id)
-- );

DELETE FROM Billing;
DELETE FROM Reservations;
DELETE FROM Guests;
-- DELETE FROM Rooms;
-- INSERT INTO Rooms (room_type, rate, is_available) VALUES ('Single', 100.00, TRUE);
-- INSERT INTO Rooms (room_type, rate, is_available) VALUES ('Double', 200.00, TRUE);
