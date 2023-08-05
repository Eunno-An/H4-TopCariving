import { Flex } from '@components/common';
import MyCar from '@pages/MyCar';
import Engine from '@pages/MyCar/Engine';
import Trim from '@pages/MyCar/Trim';
import { createBrowserRouter } from 'react-router-dom';

export const router = createBrowserRouter([
  { path: '', element: <Flex>메인화면</Flex> },
  { path: '/*', element: <Flex>Error: 해당 url에 화면이 없습니다</Flex> },
  {
    path: '/my-car',
    element: <MyCar />,
    children: [
      {
        path: 'trim',
        element: <Trim />,
      },
      {
        path: 'trim/engine',
        element: <Engine />,
      },
      {
        path: 'trim/body-type',
        element: <Flex>바디타입 선택 화면</Flex>,
      },
      {
        path: 'trim/traction',
        element: <Flex>구동방식 선택 화면</Flex>,
      },
      {
        path: 'color',
        element: <Flex>컬러선택</Flex>,
      },
      {
        path: 'option',
        element: <Flex> 선택 옵션</Flex>,
        children: [
          { path: 'genuine', element: <Flex>H genuine</Flex> },
          { path: 'performance', element: <Flex>N Performance</Flex> },
        ],
      },
    ],
  },
  { path: '/my-car/complete', element: <Flex>내 차 만들기 완성!!</Flex> },
  {
    path: '/archive',
    element: <Flex>아카이빙</Flex>,
  },
]);
