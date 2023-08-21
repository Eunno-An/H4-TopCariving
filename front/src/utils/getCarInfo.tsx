import { MyCarInfoInterface, optionKey } from '@contexts/MyCarContext';
import {
  archiveDetailInterface,
  archiveOptionDetailInterface,
} from '@pages/Archive/detail';

/**
 {
      "carOptionId": 116,
      "optionName": "현대스마트센스 I",
      "categoryDetail": "상세 품목",
      "photoUrl": "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/smart_front_collision.jpeg",
      "childOptionNames": [
        "전방 추돌방지 보조",
        "내비게이션 기반 스마트 크루즈 컨트롤",
        "고속도로 주행 보조 2"
      ],
      "positionId": null,
      "tags": []
    }
 의 형식으로 들어오는 데이터를 categoryDetail 를 키로 재세팅해서 리턴함.
 */
export const getOptionKeyInfo = (
  optionDetails: archiveOptionDetailInterface[],
) => {
  let newOptionDetail = {} as {
    [key in string]: archiveOptionDetailInterface[];
  };

  optionDetails.forEach((option) => {
    if (newOptionDetail[option.categoryDetail]) {
      newOptionDetail = {
        ...newOptionDetail,
        [option.categoryDetail]: [
          ...newOptionDetail[option.categoryDetail],
          option,
        ],
      };
    } else
      newOptionDetail = {
        ...newOptionDetail,
        [option.categoryDetail]: [option],
      };
  });

  return newOptionDetail;
};

// detail?archivingId=${archivingId} 를 통해 받아온 데이터를 myCarInfo 형식으로 변환합니다.
export const getCarInfo = ({ info }: { info: archiveDetailInterface }) => {
  const optionDetails = getOptionKeyInfo(info.optionDetails);

  const initOption = { selected: [], genuine: [], performance: [] } as {
    [key in optionKey]: { id: number; name: string; price: number }[];
  };

  optionDetails[`상세 품목`] &&
    optionDetails[`상세 품목`].forEach((item) => {
      if (item.categoryDetail === '상세 품목') {
        initOption.selected = [
          ...initOption.selected,
          { id: item.carOptionId, name: item.optionName, price: 0 },
        ];
      } else if (item.categoryDetail === 'H Genuine Accessories') {
        initOption.genuine = [
          ...initOption.genuine,
          { id: item.carOptionId, name: item.optionName, price: 0 },
        ];
      } else if (item.categoryDetail === 'N performance') {
        initOption.performance = [
          ...initOption.performance,
          { id: item.carOptionId, name: item.optionName, price: 0 },
        ];
      }
    });

  const carInfo: MyCarInfoInterface = {
    trim: {
      type: optionDetails.모델
        ? {
            id: optionDetails.모델[0].carOptionId,
            name: optionDetails.모델[0].optionName,
            price: 0,
          }
        : null,
      engine: optionDetails.엔진
        ? {
            id: optionDetails.엔진[0].carOptionId,
            name: optionDetails.엔진[0].optionName,
            price: 0,
          }
        : null,
      bodyType: optionDetails.바디타입
        ? {
            id: optionDetails.바디타입[0].carOptionId,
            name: optionDetails.바디타입[0].optionName,
            price: 0,
          }
        : null,
      traction: optionDetails.구동방식
        ? {
            id: optionDetails.구동방식[0].carOptionId,
            name: optionDetails.구동방식[0].optionName,
            price: 0,
          }
        : null,
    },
    color: {
      exteriorColor: optionDetails.외장색상
        ? {
            id: optionDetails.외장색상[0].carOptionId,
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            name: optionDetails.외장색상[0].optionName as any,
            url: optionDetails.외장색상[0].photoUrl,
            price: 0,
          }
        : null,
      interiorColor: optionDetails.내장색상
        ? {
            id: optionDetails.내장색상[0].carOptionId,
            name: optionDetails.내장색상[0].optionName,
            url: optionDetails.내장색상[0].photoUrl,
            price: 0,
          }
        : null,
    },
    option: initOption,
    price: info.totalPrice,
  };

  return carInfo;
};
