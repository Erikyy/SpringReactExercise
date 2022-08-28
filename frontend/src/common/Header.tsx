import React from 'react';
import type { FC } from 'react';
import {
  AppBar,
  Box,
  Container,
  Link,
  Menu,
  Stack,
  Toolbar,
  Typography,
} from '@mui/material';
import LanguageSelector from '@app/features/Language/LanguageSelector';
import CurrencySelector from '@app/features/Currency/CurrencySelector';
import { useTranslation } from 'react-i18next';
import { T_ORDERS, T_TV_PACKAGES } from '@app/constants';

const Header: FC = () => {
  const { t } = useTranslation();
  return (
    <AppBar
      variant='outlined'
      elevation={0}
      color='default'
      position='fixed'
      sx={{ paddingLeft: 2, paddingRight: 2 }}
    >
      <Toolbar disableGutters>
        <Stack direction='row' spacing={2}>
          <Link color='black' href='/' underline='hover'>
            {t(T_TV_PACKAGES)}
          </Link>
          <Link color='black' href='/orders' underline='hover'>
            {t(T_ORDERS)}
          </Link>
        </Stack>

        <Stack sx={{ marginLeft: 'auto' }} direction='row' spacing={2}>
          <LanguageSelector />
          <CurrencySelector />
        </Stack>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
