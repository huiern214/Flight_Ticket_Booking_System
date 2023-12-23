import React, { useState } from "react";
import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { TextField, Button, Grid, FormControl, MenuItem, Accordion, AccordionSummary, AccordionDetails } from "@mui/material"; // Added missing imports

function Passenger({ numOfPassenger, onUpdatePassengerList }) {
  const [passengers, setPassengers] = useState([]);
  const [expanded, setExpanded] = useState(null);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  const addPassenger = (e, index) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);

    const passenger = {
      passengerFirstName: data.get("firstName"),
      passengerLastName: data.get("lastName"),
      passengerDob: data.get("dob"),
      passengerGender: data.get("gender"),
      passengerEmail: data.get("email"),
      passengerPhoneNo: data.get("phone_no"),
    };
    // console.log(passenger);

    // Create a copy of the passengers array
    const updatedPassengers = [...passengers];
    updatedPassengers[index] = passenger;
    setPassengers(updatedPassengers);

    // Call the callback function to update the passenger list in the parent component (Checkout)
    onUpdatePassengerList(updatedPassengers);
  };

  return (
    <Card>
      <Box pt={1} pb={2} px={2}>
        <Box component="ul" display="flex" flexDirection="column" p={0} m={0}>
          {passengers.length === 0 ? (
            <Typography>Please fill in passenger info.</Typography>
          ) : (
            passengers.map((passenger, index) => (
              <Typography key={index} m="2%">
                <div><strong>Passenger {index + 1}:</strong></div>
                <div>Name: {passenger.passengerFirstName} {passenger.passengerLastName}</div>
                <div>DOB: {passenger.passengerDob}</div>
                <div>Gender: {passenger.passengerGender}</div>
                <div>Email: {passenger.passengerEmail}</div>
                <div>Phone No: {passenger.passengerPhoneNo}</div>
              </Typography>
            ))
          )}
          {Array.from({ length: numOfPassenger }).map((_, index) => (
            <Accordion
              key={index}
              expanded={expanded === `panel${index}`}
              onChange={handleChange(`panel${index}`)}
              style={{ marginTop: "1rem" }}
            >
              <AccordionSummary>
                <Typography>Add Passenger {index + 1}</Typography>
              </AccordionSummary>
              <AccordionDetails>
                <Box
                  component="form"
                  noValidate
                  onSubmit={(e) => addPassenger(e, index)} // Pass the index to identify the passenger being added/edited
                  sx={{ mt: 1 }}
                >
                  <Grid container spacing={2} marginTop={"2%"} marginBottom={"2%"}>
                    <Grid item xs={12} sm={6} md={6}>
                      <TextField
                        autoComplete="fname"
                        name="firstName"
                        required
                        fullWidth
                        id="firstName"
                        label="First Name"
                        autoFocus
                      />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                      <TextField
                        required
                        fullWidth
                        id="lastName"
                        label="Last Name"
                        name="lastName"
                        autoComplete="lname"
                      />
                    </Grid>
                  </Grid>
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    type="email"
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    autoFocus
                  />
                  <Grid
                    container
                    display={"flex"}
                    flexDirection={"row"}
                    spacing={2}
                    marginTop={"2%"}
                    marginBottom={"2%"}
                  >
                    <Grid item xs={12} sm={6} md={6}>
                      <TextField
                        fullWidth
                        id="dob"
                        label="DOB"
                        type="date"
                        name="dob"
                        InputLabelProps={{
                          shrink: true,
                        }}
                      />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                      <FormControl fullWidth>
                        <TextField
                          id="gender"
                          name="gender"
                          label="Gender"
                          defaultValue={""}
                          select
                        >
                          <MenuItem value="female">Female</MenuItem>
                          <MenuItem value="male">Male</MenuItem>
                          <MenuItem value="other">Other</MenuItem>
                        </TextField>
                      </FormControl>
                    </Grid>
                  </Grid>
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="phone_no"
                    label="Phone Number"
                    id="phone_no"
                    autoComplete="phone_no"
                  />
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Add
                  </Button>
                  <Button
                    onClick={() => setExpanded(null)} // Close the accordion
                  >
                    Close
                  </Button>
                </Box>
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
      </Box>
    </Card>
  );
}

export default Passenger;
