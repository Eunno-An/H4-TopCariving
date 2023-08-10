import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Flex } from '@components/common';
import { Footer, Header, NavBar } from '@components/myCar';

export const myCarUrl = [
  '/my-car/trim',
  '/my-car/trim/engine',
  '/my-car/trim/body-type',
  '/my-car/trim/traction',
  '/my-car/color',
  '/my-car/option',
  '/my-car/option/genuine',
  '/my-car/option/performance',
  '/my-car/complete',
] as string[];

const MyCar = () => {
  const [currentUrl, setCurrentUrl] = useState(
    window.location.pathname as string,
  );

  return (
    <Flex direction="column" align="center">
      <Header />
      <NavBar currentUrl={currentUrl} />
      <Flex direction="column" width={1040}>
        <Outlet />
      </Flex>
      <Footer currentUrl={currentUrl} setCurrentUrl={setCurrentUrl} />
    </Flex>
  );
};

export default MyCar;
