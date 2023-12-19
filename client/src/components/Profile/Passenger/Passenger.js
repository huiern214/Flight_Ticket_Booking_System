import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import PassengerDetails from "./PassengerDetails";

// 1. Use user_id to get all passenger_id
// 2. Use passenger_id to get all passenger details
// 3. Display all passenger details

function Passenger() {
  return (
    <Card>
      <Box pt={3} px={2}>
        <Typography variant="h6" fontWeight="bold">
          Billing Information
        </Typography>
      </Box>
      <Box pt={1} pb={2} px={2}>
        <Box component="ul" display="flex" flexDirection="column" p={0} m={0}>
          <PassengerDetails
            name="Oliver Liam"
            dob={"1999-12-31"}
            gender={"Male"}
            email={"oliver@gmail.com"}
            phone_no={"1234567890"}
          />
          <PassengerDetails
            name="Lucas Harper"
            dob={"1999-12-31"}
            gender={"Female"}
            email={"lucas@stone-tech.com"}
            phone_no={"1234567890"}
          />
          <PassengerDetails
            name="ethan james"
            dob={"1999-12-31"}
            gender={"Male"}
            email={"ethan@gmail.com"}
            phone_no={"1234567890"}
            noGutter
          />
        </Box>
      </Box>
    </Card>
  );
}

export default Passenger;