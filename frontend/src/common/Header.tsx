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
import { T_ORDERS } from '@app/constants';
import Categories from '@app/components/Categories';

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
          <Categories />
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
