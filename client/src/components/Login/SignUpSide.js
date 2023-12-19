import * as React from 'react';
import { Avatar, Button, Grid, Link, Paper, TextField, Box } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Background from '../../assets/Flight.jpg';
import { Flight } from '@mui/icons-material';
import api from '../../api/axiosConfig';
import { toast } from 'react-toastify';

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Typography color="inherit" variant="body2" component="span">
        FlyEase
      </Typography>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export default function SignUpSide() {
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    console.log({
      firstName: data.get('firstName'),
      lastName: data.get('lastName'),
      email: data.get('email'),
      password: data.get('password'),
    });
  
    try {
      const response = await api.post('/users/register', {
        firstName: data.get('firstName'),
        lastName: data.get('lastName'),
        email: data.get('email'),
        password: data.get('password'),
      });
      
      // if (response.status === 200) {
      //   // setShowModal(true); // Show modal on successful sign-up
      //   toast.success('Successfully registered user');
      // }
    } catch (error) {
    //   // console.log(error);
    //   if (error.response.status === 400) {
    //     // Duplicate email error
    //     toast.error('Email already exists');
    //   } else {
    //     // Other error
    //     toast.error('Failed to register user');
    //   }
    }
  };


  return (
    <Grid container component="main" sx={{ height: '100vh' }}>
      <Grid
        container
        item
        xs={12}
        sm={8}
        md={7}
        sx={{
          backgroundImage: `url(${Background})`,
          backgroundRepeat: 'no-repeat',
          backgroundColor: (t) =>
            t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <Grid
          item
          xs={12}
          sx={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'center',
            margin: '5%',
            marginTop: '15%',
            height: '100%',
          }}
        >
          <Flight sx={{ fontSize: '4rem', color: 'hsl(225, 50%, 48%)' }} />
          <Typography
            component="h1"
            variant="h2"
            align="center"
            color={'hsl(225, 50%, 48%)'}
          >
            FlyEase
          </Typography>
        </Grid>
      </Grid>

      <Grid
        item
        xs={12}
        sm={8}
        md={5}
        component={Paper}
        elevation={6}
        square
      >
        <Box
          sx={{
            my: 8,
            mx: 4,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, marginTop: '10%', bgcolor: 'primary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign Up
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
            <Grid container spacing={2} marginTop={'2%'} marginBottom={'2%'}>
              <Grid item xs={12} sm={6}>
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
              <Grid item xs={12} sm={6}>
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
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="new-password"
              // You can add more fields here for user registration
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container>
              <Grid item>
                <Link href="signin" variant="body2">
                  {'Already have an account? Sign In'}
                </Link>
              </Grid>
            </Grid>
            <Copyright sx={{ mt: 5 }} />
          </Box>
        </Box>
      </Grid>
    </Grid>
  );
}
