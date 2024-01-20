import React from "react";
import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import PassengerDetails from "./PassengerDetails";

function Passenger(passenger) {
  const passengerId = passenger.passenger.passengerId;
  const firstName = passenger.passenger.passengerFirstName;
  const lastName = passenger.passenger.passengerLastName;
  const email = passenger.passenger.passengerEmail;
  const passport_no = passenger.passenger.passengerPassportNo;
  const gender = passenger.passenger.passengerGender;
  const phone_no = passenger.passenger.passengerPhoneNo;

  return (
    <Card>
      <Box pt={3} px={2}>
        <Typography variant="h6" fontWeight="bold">
          Passenger Information
        </Typography>
      </Box>
      <Box pt={1} pb={2} px={2}>
        <Box component="ul" display="flex" flexDirection="column" p={0} m={0}>
          {passengerId === null ? (
            <Typography>No passenger found.</Typography>
          ) : (
            <PassengerDetails
              passengerId={passengerId}
              firstName={firstName}
              lastName={lastName}
              email={email}
              passport_no={passport_no}
              gender={gender}
              phone_no={phone_no}
            />
            )
          }
        </Box>
      </Box>
    </Card>
  );
}

export default Passenger;