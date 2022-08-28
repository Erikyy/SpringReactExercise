import React, { useEffect, useState } from 'react';
import type { FC } from 'react';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { useTranslation } from 'react-i18next';

interface SelectorProps {
  id: string;
  label: string;
  data: string[];
  value: string;
  defaultValue: string;
  onChange: (val: string) => void;
}

const Selector: FC<SelectorProps> = ({
  id,
  label,
  data,
  value,
  onChange,
  defaultValue,
}) => {
  const [open, setOpen] = useState(false);
  const [values, setValues] = useState<string[]>([]);
  const { t } = useTranslation();
  useEffect(() => {
    setValues(data);
  }, [data]);
  if (values.length == 0) {
    return null;
  }
  return (
    <FormControl sx={{ minWidth: 80 }} size='small' variant='outlined'>
      <InputLabel id={`${id}-label`}>{t(label)}</InputLabel>
      <Select
        open={open}
        onClose={() => setOpen(false)}
        onOpen={() => setOpen(true)}
        onChange={(e) => {
          onChange(e.target.value);
        }}
        labelId={`${id}-label`}
        id={`${id}`}
        value={value}
        defaultValue={defaultValue}
        label={t(label)}
      >
        {values.map((item, i) => {
          return (
            <MenuItem key={`${id}-${item}-####`} value={item}>
              {item}
            </MenuItem>
          );
        })}
      </Select>
    </FormControl>
  );
};

export default Selector;
