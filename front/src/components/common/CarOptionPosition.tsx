import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { useState } from 'react';

export interface positionInterface {
  positionId: number;
  optionName: string;
  leftPercent: string;
  topPercent: string;
}

export const CarOptionPosition = ({
  position,
  color,
}: {
  position: positionInterface[] | undefined;
  color?: string | undefined;
}) => {
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
      {position &&
        position.map((it, idx) => (
          <div key={`carPosition_${idx}`}>
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
          </div>
        ))}
      <img
        src={`https://topcariving.s3.ap-northeast-2.amazonaws.com/360/${color}/image_001.webp`}
        alt=""
      />
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
