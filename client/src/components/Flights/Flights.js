import React, { useState, useEffect } from 'react';
import dayjs from 'dayjs';
import { Grid, Typography, Box, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Select, MenuItem, ToggleButtonGroup, ToggleButton } from '@mui/material';
import { CenterFocusStrong, KeyboardArrowRight } from '@mui/icons-material';
import api from '../../api/axiosConfig';
import { toast } from 'react-toastify';
import { format, startOfWeek, endOfWeek, addWeeks } from 'date-fns'

function Flights() {
  const [weeks, setWeeks] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [selectedWeek, setSelectedWeek] = useState(null);
  const [allFlights, setAllFlights] = useState([]);
  const today = dayjs();

  const [selected, setSelected] = useState([]);

  const handleToggleChange = (event, newValue) =>{
    if (newValue !== null){
      setSelected(newValue);
    }
  };

  const fetchAllFlights = async () => {
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

  const handleWeekChange = (event) => {
    const selectedIndex = event.target.value;
    setSelectedWeek(weeks[selectedIndex]);
  }

  useEffect(() => {
    const getCurrentWeekStartDate = () => {
      const today = new Date();
      return startOfWeek(today);
    };

    const today = dayjs();
    const currentWeekStartDate = getCurrentWeekStartDate();
    const baseDate = new Date(2024, 0, 1);
    const currentWeek = startOfWeek(baseDate);

    const weeksArray = Array.from({ length: 30 }, (_, index) => {
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

    setWeeks(weeksArray);
  }, []);

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
      <Grid item ml={'7%'} mb={'5%'} xs={8} sm={8} md={3} bgcolor='rgb(248, 249, 250)'>
        <div className='WeekPicker'>
          <Typography
            variant="h2"
            align="center"
            sx={{
              width: '80%',
              margin: 'auto',
              marginTop: '30px',
              marginBottom: '30px',
              borderBottom: '2px solid #000', // Adjust the color and size as needed
              fontSize: '1.5rem',
              '@media (max-width:680px)': {
                fontSize: '1.5rem',
              },
            }}
          >
            Sort and Filter
          </Typography>
          <Typography variant="body1" component="label" sx={{ marginLeft: '10px', marginBottom: '8px' }}>
            Select a Week:
          </Typography>
          <Select
            value={weeks.findIndex((week) => week === selectedWeek)}
            onChange={handleWeekChange}
            displayEmpty
            sx={{
              marginTop: '10px',
              marginLeft: '10px',
              minWidth: '200px',
            }}
          >
            <MenuItem value="" disabled>
              Select a Week
            </MenuItem>
            {weeks.map((week, index) => (
              <MenuItem key={index} value={index}>
                {week.label}
              </MenuItem>
            ))}
          </Select>

          {/* {selectedWeek && (
            <div>
              <Typography variant="body2" sx={{ marginTop: '16px' }}>
                Selected Week:
              </Typography>
              <Typography variant="body2">
                Start Date: {format(selectedWeek.start, 'do MMM yyyy')}
              </Typography>
              <Typography variant="body2">
                End Date: {format(selectedWeek.end, 'do MMM yyyy')}
              </Typography>
            </div>
          )} */}
        </div>
      </Grid>
      {selectedWeek && (
        <FlightDetails item selectedDate={selectedWeek.start.toISOString().split('T')[0]} allFlights={allFlights} />
      )}
    </Grid>
  );
}

export default Flights;

// FlightDetails component
function FlightDetails({ selectedDate }) {
  // State variables
  const [showAll, setShowAll] = useState(false);
  const [filteredFlights, setFilteredFlights] = useState([]);

  // Handle click to toggle showing all flights
  const handleShowAllClick = () => {
    setShowAll(!showAll);
  };

  // Function to get the status of a flight
  function getStatus(flight) {    
    if (flight.passengers >= flight.totalSeats) {
      return 'Add to Waiting List';
    } else {
      return 'Book';
    }
  }

  // Fetch flights for the selected date using API
  const fetchSelectedDateFlight = async(selectedDate) => {
    try {
      const response = await api.get(`/flight/getAllFlightsByDate/${selectedDate}`); 
      setFilteredFlights(response.data);
    } catch (error) {
      console.error('Error fetching flight data:', error);
    }
  };

  // useEffect to fetch flights when the selectedDate changes
  useEffect(() => {
    if (selectedDate) {
      fetchSelectedDateFlight(selectedDate);
    }
  }, [selectedDate]);

  // Function to handle waiting list button click
  const clickWaiting = () => toast.success('Added successfully.');

  // Render FlightDetails UI
  return (
    <Box
      xs={8}
      sm={8}
      md={6}
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
      <TableContainer component={Paper} style={{
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
                    {/* Different button based on flight status */}
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
            {/* Button to show/hide all flights */}
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
              onClick={handleShowAllClick}
            >
              <span>See {showAll ? 'Less' : 'More'}</span>
              <KeyboardArrowRight sx={{ marginLeft: '0.2rem' }} />
            </Button>
          </Box>}
      </TableContainer>
    </Box>
  );
}