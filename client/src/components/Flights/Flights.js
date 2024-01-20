import React from 'react'
import dayjs from 'dayjs';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { StaticDatePicker } from '@mui/x-date-pickers/StaticDatePicker';
import { useEffect } from 'react'
import { Grid, Typography, Box, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import { KeyboardArrowRight } from '@mui/icons-material';
import { useState } from 'react';
import api from '../../api/axiosConfig';
import { toast } from 'react-toastify';
import { useSelector } from 'react-redux';

function Flights() {
  const [selectedDate, setSelectedDate] = useState(null);
  const [allFlights, setAllFlights] = useState([]);
  const userId = useSelector((state) => state.user.userId);
  // const userId = 1;
  const today = dayjs();


  // use get api to get all flights
  const fetchAllFlights = async() => {
    try {
      const response = await api.get(`/flight/getAllFlights`);
      setAllFlights(response.data);
    } catch (error) {
      console.error('Error fetching flight data:', error);
      toast.error('Error');
    }      
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };
    
  useEffect(() => {
    handleDateChange(today);
    fetchAllFlights()
      .then(() => {
        console.log('Updated allFlights:', allFlights);
      });
  }, []);
  
  return (
    <Grid container>
        <Grid item xs={12}>
          <Typography
          variant="h4"
          align="center"
          sx={{
              width: '80%',
              margin: 'auto',
              marginTop: '3%',
              marginBottom: '3%',
              fontSize: '2rem',
              '@media (max-width:680px)': {
              fontSize: '1.5rem',
              },
          }}
          >
              Book your flights today!
          </Typography>
        </Grid>
        <Grid item ml={'7%'} mb={'5%'}
          xs={8} sm={8} md={3}
          // xs={12} sm={12} md={12}
          bgcolor='rgb(248, 249, 250)'
        >
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <StaticDatePicker
                orientation='portrait'
                openTo="day"
                value={today}
                // value={selectedDate}
                onChange={handleDateChange}
                disablePast
            />
          </LocalizationProvider>
        </Grid>
        {selectedDate && (
          <FlightDetails item selectedDate={selectedDate.toISOString().split('T')[0]} allFlights={allFlights}/>
        )}
    </Grid>
  )
}

export default Flights

function FlightDetails({ selectedDate }) {

  const [showAll, setShowAll] = useState(false); // Initialize showAll state
  const [filteredFlights, setFilteredFlights] = useState([]);

  const handleShowAllClick = () => {
    setShowAll(!showAll); // Toggle showAll state
  };

  function getStatus(flight) {    
    if (flight.passengers >= flight.totalSeats) {
      return 'Add to Waiting List';
    } else {
      return 'Book';
    }
  }

  // use post api to get flights on selected date
  const fetchSelectedDateFlight = async(selectedDate) => {
    try {
      //TODO: change selectedDate to Date1 and Date2?
      const response = await api.get(`/flight/getAllFlightsByDate/${selectedDate}`); 
      setFilteredFlights(response.data);

      // setLastUpdateTime(response.headers['last-update']);
      // console.log(lastUpdateTime);
      // setLastUpdateTime(lastUpdateTime);

    } catch (error) {
      console.error('Error fetching flight data:', error);
    }
  };

  // Function to filter flights based on the flight date
  // const filterFlightsByDate = (date) => {
  //   const filteredData = allFlights.filter((flight) => flight.flightDepartureDate === date);
  //   setFilteredFlights(filteredData);
  // };

  // // add to waiting list using axios post request
  // const addToWaitingList = async (flight) => {
  //   try {
  //     await api.post('/waiting-list', {
  //       flightId: flight.id,
  //       userId: userId,
  //     });
  //     console.log('Added to waiting list');
  //     toast.success('Added successfully.');
  //   } catch (error) {
  //     console.error('Error fetching stock data:', error);
  //     toast.error('Error');
  //   }
  // } 

  // const clickWaiting = (flight) => addToWaitingList(flight);

  useEffect(() => {
    if (selectedDate) {
      fetchSelectedDateFlight(selectedDate);
    }
  }, [selectedDate]);
  const clickWaiting = () => toast.success('Added successfully.');

  return (
    <Box
    xs={8}
    sm={8}
    md={6}
    // xs={12} sm={12} md={12}
    margin={'10%'}
    mb={'3%'}
    mt={'5%'}
    sx= {{
        '@media (max-width:680px)': {
        marginTop: '15%',
      }
    }}
    >

    {/* Table for flights */}
    <TableContainer component={Paper} 
    style={{
      backgroundColor: 'rgb(255, 255, 255)',
      color: 'rgb(17, 25, 39)',
      transition: 'box-shadow 300ms cubic-bezier(0.4, 0, 0.2, 1) 0ms',
      border: '1px solid rgb(242, 244, 247)',
      backgroundImage: 'none',
      overflow: 'hidden',
      borderRadius: '20px',
    }}>
      <Typography variant="h6" m={'4% 1% 2% 3%'} fontWeight='700' lineHeight={'1.2'} fontSize={'1.0625rem'}>
          Flights
      </Typography>
      <Table>
        <TableHead>
          <TableRow sx={{
            bgcolor: 'rgb(248, 249, 250)', 
            lineHeight: '1',
            color: 'rgb(47, 55, 70)',
            '& th': {fontWeight: '550'}
          }}>
            <TableCell>Flight ID</TableCell>
            <TableCell>Date</TableCell>
            <TableCell>Time</TableCell>
            <TableCell>Price</TableCell>
            <TableCell>Seats</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        {(filteredFlights.length === 0) ? null : 
          <TableBody>
            {(filteredFlights.slice(0, showAll ? filteredFlights.length : 1).map((flight, index) =>
            <TableRow key={flight.flightId}>
              <TableCell>{flight.flightId}</TableCell>
              <TableCell>{flight.flightDepartureDate}</TableCell>
              <TableCell>{flight.flightDepartureTime}</TableCell>
              <TableCell>RM {flight.flightPrice}</TableCell>
              <TableCell>{flight.flightTotalPassengers} / {flight.flightTotalSeats}</TableCell>
              <TableCell>
                {/* get status different button */}
                {getStatus(flight) === 'Book' ? (
                  <Button href={`/checkout/${flight.flightId}`}>
                    <span className='css-wu11c1'>
                      Book
                    </span>
                  </Button>
                ) : (
                  <Button onClick={clickWaiting}>
                    <span className='css-rpg9ag'>
                      Add to Waiting List
                    </span>
                  </Button>
                )}
              </TableCell>
            </TableRow>
            ))}
        </TableBody>}
      </Table>
      {(filteredFlights.length === 0) ? <Box m={'5%'}>No flights available for the selected date.</Box> : 
      <Box p={'2%'} sx={{display: 'flex', justifyContent: 'flex-end'}}>
        <Button
          fullWidth={true}
          sx={{
            width: 'fit-content',
            border: '0',
            outline: '0',
            fontWeight: '600',
            fontSize: '0.875rem',
            lineHeight: '1.75',
            textTransform: 'none',
          }}
          onClick={handleShowAllClick} // Toggle showAll state on button click
        >
          <span>See {showAll ? 'Less' : 'More'}</span>
          <KeyboardArrowRight sx={{ marginLeft: '0.2rem' }} />
        </Button>
      </Box>}
    </TableContainer>
    </Box>
  );
}
    