import { Flex, Text } from '@components/common';
import vector455 from '@assets/images/vector455.svg';
import { useEffect, useState } from 'react';

interface navBarTextInterface {
  [key: string]: {
    text: string;
    children: {
      [key: string]: string;
    };
  };
}

const navBarText = {
  trim: {
    text: '트림선택',
    children: {
      engine: '엔진',
      'body-type': '바디타입',
      traction: '구동방식',
    },
  },
  color: {
    text: '색상선택',
    children: {
      '': '외장색상/내장색상',
    },
  },
  option: {
    text: '옵션 선택',
    children: {
      '': '선택옵션',
      genuine: 'H Genuine Accessories',
      performance: 'N Performance',
    },
  },
} as navBarTextInterface;

interface navBarProps {
  currentUrl: string;
}

export const NavBar = ({ currentUrl }: navBarProps) => {
  const [step, setStep] = useState('');
  const [detailStep, setDetailStep] = useState('');
  const [stepIdx, setStepIdx] = useState(0);

  useEffect(() => {
    const [step, detailStep] = currentUrl.split('/').slice(2);

    setStep(step);
    setDetailStep(detailStep ? detailStep : '');
    setStepIdx(Object.keys(navBarText).indexOf(step) + 1);
  }, [currentUrl]);

  return (
    <Flex backgroundColor="LightSand" height={60} justify="center">
      {step && (
        <Flex width={1024}>
          <Flex gap={12} justify="flex-start">
            <Flex gap={5} width="auto">
              <Flex
                borderRadius="50%"
                backgroundColor="Black"
                width={20}
                height={20}
              >
                <Text palette="Neutral" typo="Body4_Medium">
                  {stepIdx}
                </Text>
              </Flex>
              <li>
                <Text palette="Black" typo="Body2_Medium">
                  {navBarText[step].text}
                </Text>
              </li>
            </Flex>
            <Flex width="auto" gap={4}>
              {Object.values(navBarText[step].children).map((text, idx) => (
                <Flex key={`navbar_${idx}`} width="auto" gap={4}>
                  {idx !== 0 && (
                    <Text palette="MediumGray" typo="Body3_Medium">
                      {'>'}
                    </Text>
                  )}

                  <li key={`detailStep_${idx}`}>
                    {text === navBarText[step].children[detailStep] ? (
                      <Text palette="DarkGray" typo="Body3_Medium">
                        {text}
                      </Text>
                    ) : (
                      <Text palette="MediumGray" typo="Body3_Medium">
                        {text}
                      </Text>
                    )}
                  </li>
                </Flex>
              ))}
            </Flex>
          </Flex>

          <Flex justify="flex-end">
            {[1, 2, 3].slice(stepIdx).map((num, idx) => (
              <>
                <Flex
                  borderRadius="50%"
                  backgroundColor="LightGray"
                  width={20}
                  height={20}
                  key={`stepCircle_${idx}`}
                >
                  <Text palette="Neutral" typo="Body4_Medium">
                    {num}
                  </Text>
                </Flex>
                {num === 2 && <img src={vector455} alt="" />}
              </>
            ))}
          </Flex>
        </Flex>
      )}
    </Flex>
  );
};
