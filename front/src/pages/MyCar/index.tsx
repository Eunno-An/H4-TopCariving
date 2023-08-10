import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Flex } from '@components/common';
import { Footer, Header, NavBar } from '@components/myCar';
import { myCarFooterInterface } from '@interface/index';

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

  const [footerInfo, setFooterInfo] = useState({
    name: ['Le Blanc', ''],
    color: { exteriorColorResponses: '', interiorColorResponses: '' },
    option: [],
    price: 0,
  } as myCarFooterInterface);

  return (
    <Flex direction="column" align="center">
      <Header />
      <NavBar currentUrl={currentUrl} />
      <Flex direction="column" width={1040}>
        <Outlet context={{ footerInfo, setFooterInfo }} />
      </Flex>
      <Footer
        currentUrl={currentUrl}
        setCurrentUrl={setCurrentUrl}
        footerInfo={footerInfo}
      />
    </Flex>
  );
};

export default MyCar;
