import { defaultOptionInterface, selectOptionInterface } from '.';

export const dummyOp: defaultOptionInterface = {
  power: [
    {
      carOptionId: 1,
      optionName: '파워트레인 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '파워트레인 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '파워트레인 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  intelligent: [
    {
      carOptionId: 1,
      optionName: '지능형시스템 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 1,
      optionName: '지능형시스템 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  safety: [
    {
      carOptionId: 1,
      optionName: '안전 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  exterior: [
    {
      carOptionId: 1,
      optionName: '안전 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '안전 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '안전 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 4,
      optionName: '안전 4',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  interior: [
    {
      carOptionId: 1,
      optionName: '내장 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  seats: [
    {
      carOptionId: 1,
      optionName: '시트 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 2,
      optionName: '시트 2',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 3,
      optionName: '시트 3',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
    {
      carOptionId: 4,
      optionName: '시트 4',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  convenience: [
    {
      carOptionId: 1,
      optionName: '편리 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
  multimedia: [
    {
      carOptionId: 1,
      optionName: '멀티미디어 1',
      optionDetail: '높은 토크로 파워풀한 드라이빙이 가능합니다',
      price: 1480000,
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/daw.jpeg',
    },
  ],
} as defaultOptionInterface;

//   선택옵션 데이터

export const optionDummy: selectOptionInterface[] = [
  {
    carOptionId: 1,
    optionName: '컴포트2',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/scc.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '후방 주차 충돌방지 보조',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
      {
        carOptionId: 2,
        optionName: '후방 주차 충돌방지',
        optionDetail: '주요 주행 정보를 전면 윈드실드에.',
      },
      {
        carOptionId: 3,
        optionName: '후방 주차',
        optionDetail: '주요 주행 정보를 전면.',
      },
      {
        carOptionId: 4,
        optionName: '후방',
        optionDetail: '주요 주행 정보를.',
      },
      {
        carOptionId: 5,
        optionName: '스마트크루즈컨트롤',
        optionDetail: '주요 주행.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 2,
    optionName: '현대스마트센스',
    price: 7900000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/vibsteeringwheel.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '현대스마트센스',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 3,
    optionName: '2열 통풍 시트',
    price: 4000000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/ncss.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '2열 통풍 시트',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 4,
    optionName: '듀얼 와이드 선루프',
    price: 8900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/fca.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '듀얼 와이드 선루프',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 5,
    optionName: '빌트인 캠(보조배터리 포함)',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/lka.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '빌트인 캠(보조배터리 포함)',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
  {
    carOptionId: 6,
    optionName: '주차보조 시스템 Ⅱ',
    price: 6900000,
    photoUrl: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/bca.jpeg',
    details: [
      {
        carOptionId: 1,
        optionName: '주차보조 시스템 Ⅱ',
        optionDetail: '주차보조 윈드실드에 표시합니다.',
      },
      {
        carOptionId: 2,
        optionName: '제네시스 스마트 크루즈 컨트롤',
        optionDetail: '주요 주행 정보를 전면 윈드실드에 표시합니다.',
      },
    ],
    tags: [
      {
        tagContent: 'string',
      },
    ],
  },
];
