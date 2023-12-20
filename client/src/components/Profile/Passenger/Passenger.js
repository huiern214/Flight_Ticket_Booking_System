import React from "react";
import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import PassengerDetails from "./PassengerDetails";
import { useEffect, useState } from "react";
import api from "../../../api/axiosConfig";
import { useSelector } from "react-redux";
import { Button } from "@mui/material";
import { TextField } from "@mui/material";
import { MenuItem } from "@mui/material";
import { Grid } from "@mui/material";
import { FormControl } from "@mui/material";
import { toast } from "react-toastify";

// 1. Use user_id to get all passenger_id
// 2. Use passenger_id to get all passenger details
// 3. Display all passenger details

function Passenger() {

  const [passengers, setPassengers] = useState(null);
  const userId = useSelector((state) => state.user.userId);
  const [showModal, setShowModal] = useState(false);

  // Get all passengers of the user
  const fetchPassengers = async (userId) => {
    try {
      if (userId === null) {
        return <div>Loading passengers...</div>;
      }
      const response = await api.get(`/user/${userId}/getAllPassengers`);
      if (response.status === 200) {
        const passenger_list = response.data;
        setPassengers(passenger_list);
      }
    } catch (error) {
      console.log(error);
    }
  };

  // Add a new passenger
  const addPassenger = async (e) => {
    try {
      e.preventDefault();
      const data = new FormData(e.currentTarget);
      
      const passenger = {
        "passengerFirstName": data.get('firstName'),
        "passengerLastName": data.get('lastName'),
        "passengerDob": data.get('dob'),
        "passengerGender": data.get('gender'),
        "passengerEmail": data.get('email'),
        "passengerPhoneNo": data.get('phone_no')
      }
      console.log(passenger);
      
      const response = await api.post(`/user/${userId}/addPassenger`, passenger);
      if (response.status === 200) {
        toast.success('Passenger added successfully!');
        setShowModal(false);
        window.location.reload();
      }
    } catch (error) {
      console.log(error);
      toast.error('Error adding passenger');
    }
  };

  useEffect(() => {
    fetchPassengers(userId);
  }, [userId]);

  return (
    <Card>
      <Box pt={3} px={2}>
        <Typography variant="h6" fontWeight="bold">
          Passenger Information
        </Typography>
      </Box>
      <Box pt={1} pb={2} px={2}>
        <Box component="ul" display="flex" flexDirection="column" p={0} m={0}>
          {passengers === null ? (
            <Typography>No passengers found.</Typography>
          ) : (
            passengers.map((passenger, key) => (
              <PassengerDetails
                key={key}
                passengerId={passenger.passengerId}
                firstName={passenger.passengerFirstName}
                lastName={passenger.passengerLastName}
                email={passenger.passengerEmail}
                dob={passenger.passengerDob}
                gender={passenger.passengerGender}
                phone_no={passenger.passengerPhoneNo}
              />
            ))
          )}
          {/*add passenger  */}
          <Button onClick = {() => setShowModal(true)}>Add Passenger</Button>
          {showModal && (
          <Box component="form" noValidate onSubmit={addPassenger} sx={{ mt: 1 }}>
            <Grid container spacing={2} marginTop={'2%'} marginBottom={'2%'}>
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
            <Grid container display={'flex'} flexDirection={'row'} spacing={2} marginTop={'2%'} marginBottom={'2%'}>
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
                  <TextField id="gender" name="gender" label="Gender" defaultValue={""} select>
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
              // onClick={() => setShowModal(false)}
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Add
            </Button>
            <Button onClick={() => setShowModal(false)}>Close</Button>
          </Box>  
        )}
        </Box>
      </Box>
    </Card>
  );
}

export default Passenger;