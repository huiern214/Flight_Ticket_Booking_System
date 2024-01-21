import React, { useEffect, useState } from 'react';
import api from '../../api/axiosConfig';
import { useSelector } from 'react-redux';
import { Button, Grid, TextField } from '@mui/material';
import { Card, Box, Typography, MenuItem, FormControl } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

function Checkout() {
  const params = useParams();
  const flightId = params.flightId;
  const [availableSeats, setAvailableSeats] = useState(null);

  const userId = useSelector((state) => state.user.userId);
  const [flightInfo, setFlightInfo] = useState(null);
  const navigate = useNavigate();

  const checkKeyDown = (e) => {
    if (e.key === 'Enter') e.preventDefault();
  };

  const handleCheckout = async (e) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);

    const passenger = {
      "passengerFirstName": data.get("firstName"),
      "passengerLastName": data.get("lastName"),
      "passengerPassportNo": data.get("passportNo"),
      "passengerGender": data.get("gender"),
      "passengerEmail": data.get("email"),
      "passengerPhoneNo": data.get("phone_no"),
    };

    // console.log(passenger)

    if ((availableSeats < 0) && passenger !== null) {
      alert('No available seats or passenger info not complete.');
      return;
    } else {
      try {
        const response = await api.post('/order/createOrder', {
          "userId": userId,
          "flightId": parseInt(flightId),
          "orderTotalPrice": flightInfo.flightPrice,
          "orderPaymentMethod": "FPX",
          "passengerFirstName": data.get("firstName"),
          "passengerLastName": data.get("lastName"),
          "passengerPassportNo": data.get("passportNo"),
          "passengerGender": data.get("gender"),
          "passengerEmail": data.get("email"),
          "passengerPhoneNo": data.get("phone_no"),
        });
        
        if (response.status === 200) {
          toast.success('Successfully added to waiting list');
          navigate(`/orders`, { replace: true });
        }
      } catch (error) {
        console.log(error);
        toast.error('Failed to add waiting list');
      }
    }
  };

  useEffect(() => {
    fetchFlightInfo(flightId);
  }, [flightId]);

  const fetchFlightInfo = async (flightId) => {
    try {
      const response = await api.get(`/flight/getFlightById/${flightId}`);
      if (response.status === 200) {
        setFlightInfo(response.data);
      }

      const response2 = await api.get(`/flight/getNumAvailableSeats/${flightId}`);
      if (response2.status === 200) {
        setAvailableSeats(response2.data);
      }
    } catch (error) {
      console.log(error);
    }
  };

  if (flightInfo === null) {
    return (
      <Typography m={'10%'} variant="h5" color="primary" align="center">
        Loading flight...
      </Typography>
    );
  } else if (availableSeats === 0 || availableSeats === null) {
    return (
      navigate(`/addToWaitingList/${flightId}`, { replace: true })
    );
  } else if (userId === null) {
    return (
      <Grid container justifyContent={"center"} alignItems={"center"}>
        <Typography item m={'10%'} variant="h5" color="primary" align="center">
            Please login before proceed to checkout.
        </Typography>
        <Button item variant="contained" color="primary" href="/signin">
            Login
        </Button>
      </Grid>
    );
  }

  return (
    <Grid container justifyContent={'center'} sx={{ backgroundColor: '#F5F5F5' }}>
        {/* Flight Information */}
        <Grid item xs={12} md={7} m={"1%"}>
            <Card>
            <Typography variant="h4" color="primary" align='center' m={"3%"} mb={0}>
              <strong>CHECKOUT</strong>
            </Typography>
                <Box pt={3} px={2}>
                    <Typography variant="h6" fontWeight="bold">
                    Flight Information
                    </Typography>
                </Box>
                <Box pt={1} pb={2} px={2} display={'flex'} flexDirection={'column'} ml={"2%"}>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Flight ID:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightId}
                            </Typography>
                        </Typography>
                    </Box>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Departure:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                            {flightInfo.flightDepartureDate} {flightInfo.flightDepartureTime}
                            </Typography>
                        </Typography>    
                    </Box>               
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Arrival:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                            {flightInfo.flightArrivalDate} {flightInfo.flightArrivalTime}
                            </Typography>
                        </Typography>    
                    </Box>               
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Price:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                RM {flightInfo.flightPrice}
                            </Typography>
                        </Typography>
                    </Box> 
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Seats:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {flightInfo.flightTotalPassengers} / {flightInfo.flightTotalSeats}
                            </Typography>
                        </Typography>
                    </Box>
                </Box>
            </Card>
        </Grid>

        {/* Payment */}
        <Grid item xs={12} md={7} m={"1%"}>
          <Card>
            <Box pt={3} px={2}>
              <Typography variant="h6" fontWeight="bold">
                Payment
              </Typography>
              <Typography m="2%" variant="h6" fontWeight="bold" color="rgb(123, 128, 154)" fontSize={"1rem"}>
                Total Price: RM {flightInfo.flightPrice}
              </Typography>
              <Typography m="2%" variant="h6" fontWeight="bold" color="rgb(123, 128, 154)" fontSize={"1rem"}>
                Payment Method: FPX
              </Typography>
            </Box>
          </Card>
        </Grid>

        {/* Passenger Information */}
        <Grid item xs={12} md={7} m={"1%"} mb={"3%"}>
          <Card>
            <Box pt={3} px={2}>
                <Typography variant="h6" fontWeight="bold">
                Passenger Information
                </Typography>
                <Typography ml={"2%"} variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                  (Left seats: {availableSeats === null ? 'Loading...' : availableSeats})
                </Typography>
            </Box>
            
            {/* Passenger Information Form */}
            <Box
              component="form"
              margin={"10%"}
              ml={"4%"}
              mb={"4%"}
              onSubmit={handleCheckout} // Pass the index to identify the passenger being added/edited
              onKeyDown={(e) => checkKeyDown(e)}
              sx={{ mt: 1 }}
              >
              <Grid container spacing={2} marginTop={"1%"} marginBottom={"1%"}>
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
                marginTop={"1%"}
                marginBottom={"1%"}
              >
                <Grid item xs={12} sm={6} md={6}>
                  <TextField
                    fullWidth
                    id="passportNo"
                    label="Passport No."
                    name="passportNo"
                    required
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
                      required
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
              <Button m="5%" type="submit" variant='contained' fullWidth>Checkout</Button>
            </Box>
          </Card>
        </Grid>
      </Grid>
  );
}

export default Checkout;


