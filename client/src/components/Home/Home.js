import React from 'react'
import Aos from 'aos'
import 'aos/dist/aos.css'
import { Grid, Typography, Container, Card, CardContent, CardActions, Button } from '@mui/material'
import { useEffect } from 'react'
import Aeroplane from '../../assets/Flight2.jpg' 

function Home() {
  useEffect(() => {
    Aos.init({ duration: 2000 });
  }, []);

  return (
    <Grid container component="main" alignItems={'center'}>
      {/* Home */}
      <Typography
          data-aos='fade-up'
          data-aos-duration='2500'
          variant="h3"
          align="center"
          sx={{
            width: '80%',
            margin: 'auto',
            marginTop: '3%',
            marginBottom: '3%',
            // fontSize: '2.5rem',
            '@media (max-width:680px)': {
              fontSize: '1.5rem',
              // marginTop: '25%',
            },
          }}
        >
        Embark on an incredible journey with us and experience the world!
      </Typography>

      <Grid
        item
        xs={8}
        sm={8}
        md={6}
        sx={{
          display: 'flex',
          justifyContent: 'center',
          margin: 'auto',
          marginTop: '0%',
          objectFit: 'cover',
          '.aeoroplane': {
            borderRadius: '15rem',
            width: '100%',
            height: '40%',
          },
        }}
      >
        <img src={Aeroplane} className='aeoroplane' alt='flight' />
      </Grid>

      {/* divider */}
      <Grid
        item
        xs={12}
        sm={12}
        md={12}
        sx={{
          display: 'flex',
          justifyContent: 'center',
          margin: 'auto',
          marginTop: '0%',
          objectFit: 'cover',
          '.aeoroplane': {
            borderRadius: '15rem',
            width: '100%',
            height: '40%',
          },
        }}
      >
        <hr
          style={{
            width: '80%',
            border: '0.1rem solid #f0f4f4',
            marginTop: '3%',
            // marginBottom: '5%',
          }}
        />
      </Grid>
      
      <Grid 
      item
      m='auto'
      >
        <Support />
      </Grid>
    </Grid>
  )
}

export default Home


function Support() {
  const cards = [
    {
      title: 'Flight Bookings',
      description: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor, sit amet consectetur adipisicing elit. At!',
      // image: 'https://source.unsplash.com/random?wallpapers',
    },
    {
      title: 'Travel Insurance',
      description: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor, sit amet consectetur adipisicing elit. At!',
      // image: 'https://source.unsplash.com/random?wallpapers',
    },
    {
      title: 'Arrival Services',
      description: 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor, sit amet consectetur adipisicing elit. At!',
      // image: 'https://source.unsplash.com/random?wallpapers',
    },
  ];

  useEffect(() => {
    Aos.init({ duration: 2000 });
  }, []);

  return (
    <Container className="support" component="section">
      <Container className="sectionContainer">
        <Grid container justifyContent="center" alignItems="center">
          <Grid container alignItems="center" 
          direction="column"
          justify="center"
          >
            <Typography marginTop="3%" variant="overline" sx={{ letterSpacing: '0.8rem', textTransform: 'uppercase', fontSize: '13px', color: 'text.primary', lineHeight: '1.5rem'
            ,'@media (max-width:680px)': {
              letterSpacing: '0.5rem',
            },
            }}>
              Travel Support
            </Typography>
            <Typography variant="h3" sx={{ marginTop: '1rem', 
            '@media (max-width:680px)': {
              fontSize: '1.5rem',
              // marginTop: '25%',
              },
            }}>
              Find help with Booking
            </Typography>
            <Typography variant="body1" sx={{ marginTop: '1rem' }}>
              Lorem, ipsum dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor sit.
            </Typography>
          </Grid>
        </Grid>
        <Grid container justifyContent="center" alignItems="center" sx={{ marginTop: '2rem', marginBottom: '5rem', gap: '2%' }}>
          {cards.map((card) => (
            <Grid item xs={12} sm={5} md={3} key={card.title}>
              <Card
                sx={{
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  p: '2rem',
                  gap: '1.5rem',
                  boxShadow: '0 0.5rem 0.5rem rgba(0, 0, 0, 0.15)',
                }}
                data-aos='fade-down' data-aos-duration='2500'
              >
                {/* <CardMedia
                  component="img"
                  image={card.image}
                  alt={card.title}
                /> */}
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    {card.title}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {card.description}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button size="small">Learn More</Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Container>
  );
}

