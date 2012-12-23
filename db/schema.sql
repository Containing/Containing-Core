BEGIN TRANSACTION;
CREATE TABLE `container` (
    `id` TEXT PRIMARY KEY,
    `arrivalDateStart` DATETIME,
    `arrivalDateEnd` DATETIME,
    `arrivalTransportType` INTEGER,
    `arrivalCompany` TEXT,
    `arrivalPosition` TEXT,
    `owner` TEXT,
    `containerNr` INTEGER,
    `departureDateStart` DATETIME,
    `departureDateEnd` DATETIME,
    `departureTransportType` INTEGER,
    `departureCompany` TEXT,
    `empty` INTEGER,
    `weight` INTEGER,
    `name` TEXT,
    `kind` TEXT,
    `danger` TEXT,
    `locationId` INTEGER,
    `storageLocation` INTEGER
);
CREATE UNIQUE INDEX idx_containerNr ON container(containerNr ASC);
CREATE INDEX idx_dates ON container(arrivalDateStart ASC, arrivalDateEnd ASC, arrivalTransportType ASC);
CREATE INDEX idx_departureDate ON container(departureDateStart ASC);
CREATE INDEX idx_loc_stor ON container(locationId ASC, storageLocation DESC);
COMMIT;
