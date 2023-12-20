import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Delete, Edit } from "@mui/icons-material";
import api from "../../../api/axiosConfig";
import { toast } from 'react-toastify';
import { MenuItem } from "@mui/material";
import { TextField } from "@mui/material";
import { Grid } from "@mui/material";
import { useState } from "react";
import { FormControl } from "@mui/material";

function PassengerDetails({ passengerId, firstName, lastName, email, dob, gender, phone_no, noGutter }) {

  const [showModal, setShowModal] = useState(false);
  
  // Delete a passenger
  const deletePassenger = async () => {
    try {
      const response = await api.delete(`/user/deletePassenger/${passengerId}`);
      if (response.status === 200) {
        console.log(response.data);
        toast.success('Passenger deleted successfully!');
        window.location.reload();
      }
    } catch (error) {
      console.log(error);
      toast.error('Error deleting passenger');
    }
  };

  // Edit a passenger
  const updatePassenger = async (e) => {
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
        
    try {
      const response = await api.post(`/user/${passengerId}/updatePassenger`, passenger);
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
      mb={noGutter ? 0 : 1}
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
            <Box mr={1}>
              <Button variant="text" color="error" onClick={deletePassenger}>
                <Delete />
                <strong>Delete</strong>
              </Button>
            </Box>
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
            Date of Birth:&nbsp;&nbsp;&nbsp;
            <Typography variant="caption" fontWeight="bold" textTransform="capitalize" color={"rgb(52, 71, 103)"}>
              {dob}
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
        // passengerId, firstName, lastName, email, dob, gender, phone_no
        <Box component="form" noValidate onSubmit={updatePassenger} sx={{ mt: 1 }}>
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
                id="dob"
                label="DOB"
                type="date"
                name="dob"
                defaultValue={dob}
                InputLabelProps={{
                  shrink: true,
                }}
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