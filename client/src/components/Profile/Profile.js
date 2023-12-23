import React, { useEffect, useState } from 'react';
import api from '../../api/axiosConfig';
import { useSelector } from 'react-redux';
import { Grid } from '@mui/material';
import Passenger from './Passenger/Passenger';
import { Card, Box, Typography } from '@mui/material';

function Profile() {
  const [profileData, setProfileData] = useState(null);
  const userId = useSelector((state) => state.user.userId);

  useEffect(() => {
    fetchUserProfile(userId);
  }, [userId]);

  const fetchUserProfile = async (userId) => {
    try {
      if (userId === null) {
        return (
          <Grid container justifyContent={"center"} alignItems={"center"}>
              <Typography item m={'10%'} variant="h5" color="primary" align="center">
                Please login to view your profile
              </Typography>
          </Grid>
        );
      }
      const response = await api.get(`/user/${userId}`);
      if (response.status === 200) {
        const profileData = response.data;
        setProfileData(profileData);
      }
    } catch (error) {
      console.log(error);
    }
  };

  if (!profileData) {
    return (
      <Grid container justifyContent={"center"} alignItems={"center"}>
        <Typography item m={'10%'} variant="h5" color="primary" align="center">
          Loading profile...
        </Typography>
      </Grid>
    );
  }

  return (
    <Grid container justifyContent={'center'} sx={{ backgroundColor: '#F5F5F5' }}>
        <Grid item xs={12} md={7} m={"2%"}>
            <Card>
                <Box pt={3} px={2}>
                    <Typography variant="h6" fontWeight="bold">
                    Personal Information
                    </Typography>
                </Box>
                <Box pt={1} pb={2} px={2} display={'flex'} flexDirection={'column'}>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            UID:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {profileData.userId}
                            </Typography>
                        </Typography>
                    </Box>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Name:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {profileData.name}
                            </Typography>
                        </Typography>
                    </Box>
                    <Box mb={1} lineHeight={0}>
                        <Typography variant="caption" fontWeight="bold" color="rgb(123, 128, 154)">
                            Email:&nbsp;&nbsp;&nbsp;
                            <Typography variant="caption" fontWeight="normal" color={"black"}>
                                {profileData.email}
                            </Typography>
                        </Typography>    
                    </Box>                
                </Box>
            </Card>
        </Grid>
        {/* <Grid item xs={12} md={7} mb={"2%"}>
            <Passenger />
        </Grid> */}
    </Grid>
  );
}

export default Profile;
