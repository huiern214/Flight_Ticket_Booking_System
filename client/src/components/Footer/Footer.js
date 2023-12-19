import * as React from 'react';
import { Box, Grid, Link, Typography, Container, IconButton } from '@mui/material';
import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import InstagramIcon from '@mui/icons-material/Instagram';
import { Flight } from '@mui/icons-material';

// Replace these with your own social media URLs
const socialMediaLinks = {
  facebook: '#',
  twitter: '#',
  instagram: '#',
};

const Footer = () => {
  return (
    <Box
      sx={{
        bgcolor: 'background.paper',
        color: 'text.secondary',
        py: 3,
        borderTop: '1px solid',
        borderColor: 'divider',
      }}
    >
      <Container maxWidth={false}>
        <Grid container spacing={2} justifyContent="space-between">
          <Grid item xs={12} sm={6} md={3} sx={{ display: 'flex' }}>
            <Flight sx={{ fontSize: '2rem', marginRight: '0.5rem'}} />
            <Typography variant="h6" color="text.primary" gutterBottom>
              FlyEase
            </Typography>          
          </Grid>
          <Grid item xs={6} sm={3} md={3}>
            <Typography variant="h6" color="text.primary" gutterBottom>
              Product
            </Typography>
            <Link href="#" color="inherit" display="block">Flight</Link>
            <Link href="#" color="inherit" display="block">Hotel</Link>
            <Link href="#" color="inherit" display="block">Pricing</Link>
            <Link href="#" color="inherit" display="block">FAQ</Link>
          </Grid>
          <Grid item xs={6} sm={3} md={3}>
            <Typography variant="h6" color="text.primary" gutterBottom>
              Company
            </Typography>
            <Link href="/" color="inherit" display="block">About Us</Link>
            <Link href="#" color="inherit" display="block">Careers</Link>
            <Link href="#" color="inherit" display="block">Airline Partners</Link>
            <Link href="#" color="inherit" display="block">Terms of Service</Link>
          </Grid>
          <Grid item xs={6} sm={3} md={3}>
            <Typography variant="h6" color="text.primary" gutterBottom>
              Social Media
            </Typography>
            <IconButton aria-label="Facebook" color="inherit" component="a" href={socialMediaLinks.facebook}>
              <FacebookIcon />
            </IconButton>
            <IconButton aria-label="Twitter" color="inherit" component="a" href={socialMediaLinks.twitter}>
              <TwitterIcon />
            </IconButton>
            <IconButton aria-label="Instagram" color="inherit" component="a" href={socialMediaLinks.instagram}>
              <InstagramIcon />
            </IconButton>
          </Grid>
        </Grid>
        <Typography variant="body2" color="text.secondary" align="center" sx={{ pt: 4 }}>
          © 2023 FlyEase. All rights reserved.
        </Typography>
      </Container>
    </Box>
  );
};

export default Footer;