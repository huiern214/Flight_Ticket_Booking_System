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
import { MenuItem, FormControl, TextField } from '@mui/material';
import { format, startOfWeek, endOfWeek, addWeeks } from 'date-fns'

function Flights() {
  const [selectedDate, setSelectedDate] = useState(null);
  const userId = useSelector((state) => state.user.userId);
  const today = dayjs();

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };
    
  useEffect(() => {
    handleDateChange(today);
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
                value={selectedDate}
                onChange={handleDateChange}
                disablePast
            />
          </LocalizationProvider>
        </Grid>
        {selectedDate && (
          <FlightDetails item selectedDate={selectedDate.format('YYYY-MM-DD')}/>
        )}
    </Grid>
  )
}

export default Flights

function FlightDetails({ selectedDate }) {

  const [showAll, setShowAll] = useState(false); // Initialize showAll state
  const [filteredFlights, setFilteredFlights] = useState([]);
  const [selectedWeekIndex, setSelectedWeekIndex] = useState("");
  const [condition, setCondition] = useState("");

  // Function to get the current week's start date
  const getCurrentWeekStartDate = () => {
    const today = new Date();
    return startOfWeek(today);
  };

  // Create an array of predefined weeks with start and end dates
  const today = dayjs();
  const currentWeekStartDate = getCurrentWeekStartDate();
  // const baseDate = new Date(2024, 0, 1);
  // const currentWeek = startOfWeek(baseDate);
  const currentWeek = startOfWeek(new Date(today));

  const weeksArray = Array.from({ length: 20 }, (_, index) => {
    const weekStart = startOfWeek(addWeeks(currentWeek, index));
    const weekEnd = endOfWeek(addWeeks(currentWeek, index));

    // Filter out weeks before the current week
    if (today.isAfter(dayjs(currentWeekStartDate))) {
      return {
        label: `Week ${index + 1} (${format(weekStart, 'do MMM yyyy')} - ${format(weekEnd, 'do MMM yyyy')})`,
        start: weekStart,
        end: weekEnd,
      };
    }

    return null;
  }).filter(Boolean); // Remove null entries

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
      const response = await api.get(`/flight/getAllFlightsByDate/${selectedDate}`); 
      setFilteredFlights(response.data);
      setCondition("date");
    } catch (error) {
      console.error('Error fetching flight data:', error);
    }
  };

  // fetch all flights between Date1 and Date2
  const fetchFlightsBetweenDates = async(startDate, endDate) => {
    try {
      const response = await api.get(`/flight/getAllFlightsBetweenDates/startDate=${startDate}&endDate=${endDate}`);
      setFilteredFlights(response.data);
    } catch (error) {
      console.error('Error fetching flight data:', error);
      toast.error('Error');
    }
  };
  // use get api to get all flights
  const fetchAllFlights = async() => {
    try {
      const response = await api.get(`/flight/getAllFlights`);
      setFilteredFlights(response.data);
      setCondition("all");
    } catch (error) {
      console.error('Error fetching flight data:', error);
      toast.error('Error');
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
    } else {
      fetchAllFlights();
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
    mt={'2%'}
    sx= {{
        '@media (max-width:680px)': {
        marginTop: '15%',
      }
    }}
    >
    <Grid item mb={"2%"} xs={12}
      sx={{
        display: 'flex',
        alignItems: 'center',
      }}
    >
      <Button xs={6} sm={6} md={6} onClick={fetchAllFlights}>Show All Flights</Button>
      <FormControl xs={6} sm={6} md={6}>
        <TextField
          select
          label='SELECT WEEK'
          name='week'
          value={selectedWeekIndex}
          onChange={(event) => {
            setSelectedWeekIndex(event.target.value);
            setCondition("week");
            // Here, you can call your API with the start and end dates for the selected week
            if (event.target.value !== '') {
              const selectedWeek = weeksArray[event.target.value];
              const startDate = selectedWeek.start.toISOString().split('T')[0];
              const endDate = selectedWeek.end.toISOString().split('T')[0];
              // Call your API using startDate and endDate
              fetchFlightsBetweenDates(startDate, endDate);
            }
          }}
          sx={{
            marginLeft: '10px',
            minWidth: '200px',
          }}
        >
          <MenuItem value="">
            <em>Select Week</em>
          </MenuItem>
          {weeksArray.map((week, index) => (
            <MenuItem key={index} value={index}>
              {week.label}
            </MenuItem>
          ))}
        </TextField>
      </FormControl>
    </Grid>
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
          {condition === "date" ? ` (${selectedDate})` : null}
          {condition === "week" ? ` (Week ${selectedWeekIndex + 1})` : null}
          {condition === "all" ? ` (All)` : null}
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
            <TableCell>Departure</TableCell>
            <TableCell>Arrival</TableCell>
            <TableCell>Price</TableCell>
            <TableCell>Seats</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        {(filteredFlights.length === 0) ? null : 
          <TableBody>
            {(filteredFlights.slice(0, showAll ? filteredFlights.length : 5).map((flight, index) =>
            <TableRow key={flight.flightId}>
              <TableCell>{flight.flightId}</TableCell>
              <TableCell>{flight.flightDepartureDate} {flight.flightDepartureTime}</TableCell>
              <TableCell>{flight.flightArrivalDate} {flight.flightArrivalTime}</TableCell>
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
    