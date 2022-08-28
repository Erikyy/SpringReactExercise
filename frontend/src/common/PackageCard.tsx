import React from 'react';
import type { FC } from 'react';
import {
  Box,
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  Typography,
} from '@mui/material';
import type { Package } from '@app/types/Package';
import { useTranslation } from 'react-i18next';
import { useAppSelector } from '@app/store/RootStore';
import { T_MAKE_ORDER } from '@app/constants';
import { useNavigate } from 'react-router-dom';
import getSymbolFromCurrency from 'currency-symbol-map';
import OrderService from '@app/service/OrderService';

interface PackageCardProps {
  data: Package;
  onOrderMade: (id: number) => void;
}

const PackageCard: FC<PackageCardProps> = ({ data, onOrderMade }) => {
  const activeCurrency = useAppSelector(
    (state) => state.currencies.activeCurrency,
  );
  const navigate = useNavigate();
  const { t } = useTranslation();
  return (
    <Card style={{ minHeight: 300 }}>
      <CardActionArea>
        <CardContent>
          <Typography align='center' fontSize={30}>
            {data.packageType.toString().charAt(0) +
              data.packageType.toString().substring(1).toLowerCase()}
          </Typography>
          <Typography align='center'>{data.descriptions[0].content}</Typography>
          <Typography>
            {data.price} {getSymbolFromCurrency(activeCurrency)}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions
        sx={{
          display: 'flex',
          justifyContent: 'center',
        }}
      >
        <Button
          onClick={(e) => {
            e.preventDefault();
            onOrderMade(data.id);
          }}
          variant='contained'
        >
          {t(T_MAKE_ORDER)}
        </Button>
      </CardActions>
    </Card>
  );
};

export default PackageCard;
