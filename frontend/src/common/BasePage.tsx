import React from 'react';
import type { FC } from 'react';
import { Box, Container, Toolbar } from '@mui/material';
import { Outlet } from 'react-router-dom';
import Header from './Header';

const BasePage: FC = () => {
  return (
    <Container>
      <Header />
      <Toolbar />
      <Toolbar />
      <Outlet />
    </Container>
  );
};

export default BasePage;
