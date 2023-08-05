import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Flex } from '@components/common';
import { Footer, Header, NavBar } from '@components/myCar';

export interface footerInterface {
  name: string;
  color: string[];
  option: { outer: string; inner: string };
  price: number;
}

const MyCar = () => {
  const [footerInfo, setFooterInfo] = useState({
    name: 'Le Blanc',
    color: [],
    option: { outer: '', inner: '' },
    price: 47720000,
  } as footerInterface);

  return (
    <Flex direction="column" align="center">
      <Header />
      <NavBar />
      <Flex direction="column" padding="0 120px">
        <Outlet context={{ footerInfo, setFooterInfo }} />
      </Flex>
      <Footer footerInfo={footerInfo} />
    </Flex>
  );
};

export default MyCar;
