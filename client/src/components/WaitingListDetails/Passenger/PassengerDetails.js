import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Edit } from "@mui/icons-material";
import api from "../../../api/axiosConfig";
import { toast } from 'react-toastify';
import { MenuItem } from "@mui/material";
import { TextField } from "@mui/material";
import { Grid } from "@mui/material";
import { useState } from "react";
import { FormControl } from "@mui/material";

function PassengerDetails({ passengerId, firstName, lastName, email, passport_no, gender, phone_no}) {

  const [showModal, setShowModal] = useState(false);
  
  // Edit a passenger
  const updatePassenger = async (e) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);

    const passenger = {
      "passengerFirstName": data.get('firstName'),
      "passengerLastName": data.get('lastName'),
      "passengerPassportNo": data.get('passport_no'),
      "passengerGender": data.get('gender'),
      "passengerEmail": data.get('email'),
      "passengerPhoneNo": data.get('phone_no')
    }
    console.log(passenger);

    try {
      const response = await api.post(`/passenger/updatePassenger/${passengerId}`, passenger);
      if (response.status === 200) {
        toast.success('Passenger updated successfully!');
        setShowModal(false);
        window.location.reload();
      }
    } catch (error) {
      toast.error('Error updating passenger');
    }
  }

  return (
    <Box
      component="li"
      display="flex"
      justifyContent="space-between"
      alignItems="flex-start"
      borderRadius="lg"
      p={3}
      mt={0}
    >
      <Box width="100%" display="flex" flexDirection="column">
        <Box
          display="flex"
          justifyContent="space-between"
          alignItems={{ xs: "flex-start", sm: "center" }}
          flexDirection={{ xs: "column", sm: "row" }}
          mb={2}
        >
          <Typography variant="button" fontWeight="bold" textTransform="capitalize">
            {firstName} {lastName}
          </Typography>

          <Box display="flex" alignItems="center" mt={{ xs: 2, sm: 0 }} ml={{ xs: -1.5, sm: 0 }}>
            <Button variant="text" onClick={setShowModal}>
              <Edit />
              <strong>Edit</strong>
            </Button>
          </Box>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Gender:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" textTransform="capitalize" color={"rgb(52, 71, 103)"}>
              {gender}
            </Typography>
          </Typography>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Passport No.:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" textTransform="capitalize" color={"rgb(52, 71, 103)"}>
              {passport_no}
            </Typography>
          </Typography>
        </Box>
        <Box mb={1} lineHeight={0}>
          <Typography variant="caption" color="rgb(123, 128, 154)">
            Email Address:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" color={"rgb(52, 71, 103)"}>
              {email}
            </Typography>
          </Typography>
        </Box>
        <Typography variant="caption" color="rgb(123, 128, 154)">
          Phone Number:&nbsp;&nbsp;&nbsp;
          <Typography variant="caption" fontWeight="bold" color={"rgb(52, 71, 103)"}>
            {phone_no}
          </Typography>
        </Typography>
      </Box>
      {showModal && (
        // passengerId, firstName, lastName, email, passport_no, gender, phone_no
        <Box width={"100%"} component="form" noValidate onSubmit={updatePassenger} sx={{ mt: 1 }}>
          <Grid container spacing={2} marginTop={'2%'} marginBottom={'2%'}>
            <Grid item xs={12} sm={6}>
              <TextField
                name="firstName"
                required
                fullWidth
                id="firstName"
                label="First Name"
                defaultValue={firstName}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="lastName"
                label="Last Name"
                name="lastName"
                autoComplete="lname"
                defaultValue={lastName}
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
            defaultValue={email}
          />
          <Grid container display={'flex'} flexDirection={'row'} spacing={2} marginTop={'2%'} marginBottom={'2%'}>
            <Grid item xs={12} sm={6} md={6}>
              <TextField
                fullWidth
                id="passport_no"
                label="Passport No."
                name="passport_no"
                defaultValue={passport_no}
                required
              />
            </Grid>
            <Grid item xs={12} sm={6} md={6}>
              <FormControl fullWidth>
                <TextField id="gender" name="gender" label="Gender" defaultValue={gender} select>
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
            type="phone_no"
            id="phone_no"
            autoComplete="phone_no"
            defaultValue={phone_no}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Update
          </Button>
          <Button onClick={() => setShowModal(false)}>Close</Button>
        </Box>  
      )}  
    </Box>
  );
}

export default PassengerDetails;