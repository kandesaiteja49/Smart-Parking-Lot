Smart Parking Lot System
A backend system for managing a smart multi-floor parking lot — handles vehicle entry/exit, spot allocation by vehicle size, real-time availability, and fee calculation. Built using core OOP principles and design patterns.

Folder Structure
smart-parking-lot/
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── smartparking/
    │               ├── ParkingLotMain.java          ← Entry point (Client Layer)
    │               ├── enums/
    │               │   └── SpotType.java            ← SMALL | MEDIUM | LARGE
    │               ├── model/
    │               │   ├── Vehicle.java             ← Abstract base class
    │               │   ├── Car.java                 ← Extends Vehicle
    │               │   ├── Bike.java                ← Extends Vehicle
    │               │   ├── Bus.java                 ← Extends Vehicle
    │               │   ├── ParkingSpot.java         ← One physical spot (HAS-A Vehicle)
    │               │   └── Floor.java               ← HAS-A list of ParkingSpots
    │               ├── factory/
    │               │   └── VehicleFactory.java      ← Factory Pattern
    │               ├── strategy/
    │               │   ├── Payment.java             ← Strategy Interface
    │               │   ├── CarPay.java              ← Rs.50/hr
    │               │   ├── BikePay.java             ← Rs.20/hr
    │               │   └── BusPay.java              ← Rs.100/hr
    │               ├── ticket/
    │               │   └── Ticket.java              ← Entry record (entryTime, vehicle, spot)
    │               └── lot/
    │                   └── ParkingLot.java          ← Singleton Controller
    └── test/
        └── java/
            └── com/
                └── smartparking/                    ← Unit tests (to be added)
Functional Requirements Coverage
Requirement	Implementation
Spot allocation by vehicle size	SpotType enum + getRequiredSpotType() in each Vehicle + getFreeSpot(SpotType) in Floor
Check-in / Check-out	parTicket() records entry time; exitvechile() computes duration
Fee calculation	Strategy Pattern — CarPay, BikePay, BusPay (hour-based, min 1 hr)
Real-time availability	printAvailability() shows per-floor free counts per spot type
Concurrency handling	Thread-safe Singleton (volatile + double-checked locking), synchronized on park/unpark/parTicket/exit
Active ticket tracking	ConcurrentHashMap<Integer, Ticket> in ParkingLot
Design Patterns Used
Pattern	Where	Why
Singleton	ParkingLot	Only one parking lot in the system; thread-safe global access
Factory	VehicleFactory	Centralizes vehicle creation; client decoupled from new Car()/new Bike()/new Bus()
Strategy	Payment → CarPay, BikePay, BusPay	Swappable fee logic per vehicle type; Open-Closed Principle
Composition	Floor HAS-A ParkingSpot; ParkingSpot HAS-A Vehicle	Models real-world structure; clear ownership
OOP Principles
Abstraction — Vehicle (abstract class), Payment (interface)
Inheritance — Car, Bike, Bus extend Vehicle
Encapsulation — Internal state (isFree, activeTickets) is private/controlled
Composition — ParkingLot → Floor → ParkingSpot → Vehicle
Vehicle Types & Spot Mapping
Vehicle	Spot Type	Fee Rate
Bike	SMALL	Rs. 20 / hour
Car	MEDIUM	Rs. 50 / hour
Bus	LARGE	Rs. 100 / hour
Minimum charge is 1 hour regardless of duration.

How to Run
Prerequisites
Java 8 or above
No external dependencies
Compile
find src -name "*.java" > sources.txt
javac -d out @sources.txt
Run
java -cp out com.smartparking.ParkingLotMain
Sample Output
--- Real-Time Parking Availability ---
Floor 0 | SMALL(Bike): 2 | MEDIUM(Car): 3 | LARGE(Bus): 1
Floor 1 | SMALL(Bike): 1 | MEDIUM(Car): 2 | LARGE(Bus): 1
Active tickets: 0

Checked in: VehicleType:car  VehicleNo:11234 Floor:0 SpotId:3 SpotType:MEDIUM
Checked in: VehicleType:car  VehicleNo:343   Floor:0 SpotId:4 SpotType:MEDIUM
Checked in: VehicleType:bike VehicleNo:143   Floor:0 SpotId:1 SpotType:SMALL
Checked in: VehicleType:bus  VehicleNo:999   Floor:0 SpotId:6 SpotType:LARGE

VehicleType:car VehicleNo:11234 ... | Fee: Rs.50.0
VehicleType:car VehicleNo:343   ... | Fee: Rs.50.0
VehicleType:bike VehicleNo:143  ... | Fee: Rs.20.0
VehicleType:bus VehicleNo:999   ... | Fee: Rs.100.0
Architecture Overview
ParkingLot (Singleton)
 ├── Floor[]
 │    └── ParkingSpot[]  (SMALL / MEDIUM / LARGE)
 │          └── Vehicle  (Bike / Car / Bus)
 ├── Ticket[]            (active check-ins)
 ├── VehicleFactory      (creates vehicles)
 └── Payment (Strategy)
       ├── CarPay
       ├── BikePay
       └── BusPay
Possible Extensions
Add Truck vehicle with an XLARGE spot type
Persist tickets to a database (replace in-memory map)
REST API layer (Spring Boot) on top of the lot controller
QR-code based ticket generation
Admin dashboard for real-time floor monitoring
