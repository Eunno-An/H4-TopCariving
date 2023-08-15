import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { useState } from 'react';

const position = [
  {
    positionId: 1,
    optionName: '2열 통풍시트',
    leftPercent: '65%',
    topPercent: '35%',
  },
  {
    positionId: 2,
    optionName: '듀얼 와이드 선루프',
    leftPercent: '55%',
    topPercent: '21%',
  },
  {
    positionId: 3,
    optionName: '빌트인 캠',
    leftPercent: '45%',
    topPercent: '28%',
  },
  {
    positionId: 4,
    optionName: '듀얼 머플러 패키지',
    leftPercent: '79%',
    topPercent: '58%',
  },
  {
    positionId: 5,
    optionName: '빌트인 공기청정기',
    leftPercent: '46%',
    topPercent: '34%',
  },
  {
    positionId: 6,
    optionName: '사이드스텝',
    leftPercent: '65%',
    topPercent: '66%',
  },
  {
    positionId: 7,
    optionName: '적외선 무릎워머',
    leftPercent: '50%',
    topPercent: '48%',
  },
  {
    positionId: 8,
    optionName: '차량 보호 필름',
    leftPercent: '59%',
    topPercent: '36%',
  },
  {
    positionId: 9,
    optionName: '20인치 다크 스퍼터링 휠',
    leftPercent: '51%',
    topPercent: '67%',
  },
  {
    positionId: 10,
    optionName: '20인치 블랙톤 전면 가공 휠',
    leftPercent: '51%',
    topPercent: '67%',
  },
  {
    positionId: 11,
    optionName: '알콘(alcon) 단조 브레이크 & 20인치 휠 패키지',
    leftPercent: '51%',
    topPercent: '67%',
  },
];

export const CarOptionPosition = () => {
  const [currrentOption, setCurrentOption] = useState<string>('');
  const [isHover, setIsHover] = useState(false);
  const onPositionEnterHandler = (optionName: string) => {
    setCurrentOption(optionName);
    setIsHover(true);
  };
  const onPositionOutHandler = () => {
    setCurrentOption('');
    setIsHover(false);
  };
  return (
    <CarImgBox>
      {position.map((it) => (
        <>
          <OptionPoint
            leftPosition={it.leftPercent}
            topPositipon={it.topPercent}
            onMouseEnter={() => onPositionEnterHandler(it.optionName)}
            onMouseOut={() => onPositionOutHandler()}
          />
          {isHover && currrentOption === it.optionName && (
            <OptionInfoBox
              leftPosition={parseInt(it.leftPercent)}
              topPositipon={parseInt(it.topPercent)}
              onMouseOut={() => onPositionOutHandler()}
            >
              {it.optionName}
            </OptionInfoBox>
          )}
        </>
      ))}
      <img src={`/image/exterior/black/image_001.png`} alt="" />
    </CarImgBox>
  );
};

const OptionInfoBox = styled.div<{
  leftPosition: number;
  topPositipon: number;
}>`
  ${theme.typo.Body3_Medium}

  position: absolute;

  background-color: ${theme.palette.Primary};
  color: ${theme.palette.White};

  white-space: nowrap;

  border-radius: 30px;

  border-top-left-radius: 3px;

  padding: 10px;

  top: ${(props) => `${props.topPositipon + 5.5}%`};
  left: ${(props) => `${props.leftPosition + 3}%`};
`;

const CarImgBox = styled.div`
  position: relative;

  width: 940px;
  height: 514px;
`;

const OptionPoint = styled.div<{
  leftPosition: string;
  topPositipon: string;
}>`
  position: absolute;

  top: ${(props) => props.topPositipon};
  left: ${(props) => props.leftPosition};
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: ${theme.palette.OptionBlue};

  animation: pointScale 1.3s linear infinite;

  cursor: pointer;

  @keyframes pointScale {
    0% {
      opacity: 1;
    }
    100% {
      opacity: 0;
      width: 30px;
      height: 30px;
    }
  }
`;
