import * as React from 'react';
import { Avatar, Button, Checkbox, FormControlLabel, Grid, Link, Paper, TextField, Box } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Background from '../../assets/Flight.jpg';
import { Flight } from '@mui/icons-material';
import api from '../../api/axiosConfig';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { toast } from 'react-toastify';
import { loginFailure, loginSuccess } from '../../redux/user/userActions';


function Copyright(props) {

  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="/">
        FlyEase
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export default function SignInSide() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    // TEMPORARY
    // navigate(`/`, { replace: true }); // Redirect to the home page
      
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    console.log({
      email: data.get('email'),
      password: data.get('password'),
    });
    try {
      const response = await api.post('/user/login', {
        email: data.get('email'),
        password: data.get('password'),
      });
      
      if (response.status === 200) {
        const userPermission = response.data; // Assuming the response contains the user ID
        dispatch(loginSuccess(userPermission.userId, userPermission.permission)); // Dispatch the login action with the user ID
        toast.success('Successfully logged in');
        if (userPermission.permission === 'admin') {
          navigate(`/flight_management`, { replace: true }); // Redirect to the flight_management page
        } else { 
          navigate(`/`, { replace: true }); // Redirect to the home page
        }
      }
    } catch (error) {
      console.log(error);
      dispatch(loginFailure("Incorrect login credentials"));
      toast.error('Incorrect login credentials');
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
          <Grid item xs={12} sx={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', margin: '5%', marginTop: '15%', height: '100%' }}>
            <Flight sx={{ fontSize: '4rem', color: "hsl(225, 50%, 48%)" }} /> 
            <Typography component="h1" variant="h2" align="center" color={"hsl(225, 50%, 48%)"}>
              FlyEase
            </Typography>
          </Grid>
        </Grid>
 
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
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
              Sign in
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                type="email"
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
                autoComplete="current-password"
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
                sx={{
                  display: 'flex',
                  flexDirection: 'row',
                  justifyContent: 'flex-start',
                  alignItems: 'center',
                }}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link href="#" variant="body2">
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link href="signup" variant="body2">
                    {"Don't have an account? Sign Up"}
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