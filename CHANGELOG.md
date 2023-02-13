# Changelog

## 1.0.0 (2023-02-13)


### ⚠ BREAKING CHANGES

* Changed package name

### Features

* Added AppError entity ([933b8e1](https://github.com/GZaccaroni/smart-parking-frontend/commit/933b8e1bf227d7ea6ee91eed33d9b60fbb42adfe))
* Added data,presentation,domain modules ([6866aa6](https://github.com/GZaccaroni/smart-parking-frontend/commit/6866aa6217c0c3cecbe493baadb6871e80c7e744))
* Added DI plugin Koin ([36774f3](https://github.com/GZaccaroni/smart-parking-frontend/commit/36774f385f1f8b755f2f07f5a12a4dbdbe038440))
* Added geo position entity to domain ([084ff34](https://github.com/GZaccaroni/smart-parking-frontend/commit/084ff34df8751b48e4fe1f05630c21fd12f17899))
* Added method to check parking slot staleness ([82293ef](https://github.com/GZaccaroni/smart-parking-frontend/commit/82293ef4e09d19f36835e23e48b389c4c478db47))
* Added missing App Errors ([670a91f](https://github.com/GZaccaroni/smart-parking-frontend/commit/670a91f9980b6ed190a4760c556e225fb1c967ea))
* Added NewUser containing info about the new user to be created ([fac2a0b](https://github.com/GZaccaroni/smart-parking-frontend/commit/fac2a0b6b8a7f967119bde36e730fe6ac6acc060))
* Added parking slot entity to domain ([3e29cc4](https://github.com/GZaccaroni/smart-parking-frontend/commit/3e29cc4313a4663da40af189598f20e3434c2fd7))
* Added parking slot repository ([f68028b](https://github.com/GZaccaroni/smart-parking-frontend/commit/f68028bde7b7af907458f00a753dac3eecc0b692))
* Added user entity to domain ([1f16165](https://github.com/GZaccaroni/smart-parking-frontend/commit/1f161650a01784c3695d64fd5a3bf925e702eee7))
* Added user repository ([8da6f38](https://github.com/GZaccaroni/smart-parking-frontend/commit/8da6f38e5118f878612943fae468868b0fa06b4f))
* Added UserCredentials and AuthState ([bb7a3ab](https://github.com/GZaccaroni/smart-parking-frontend/commit/bb7a3abf6981418fe7b0d83ae544184c2e1a76d1))
* Implemented  change user password use case ([052276a](https://github.com/GZaccaroni/smart-parking-frontend/commit/052276a3fcad986c6a8d904c55d6c3cbc3d7cabf))
* Implemented base use cases ([f1b3566](https://github.com/GZaccaroni/smart-parking-frontend/commit/f1b35667a3ca93e61885fe7608095aa4e623ef47))
* Implemented delete user use case ([dcc73ca](https://github.com/GZaccaroni/smart-parking-frontend/commit/dcc73caa1ad739de5369703092ca75d99eb903ad))
* Implemented documentation generation with Dokka ([1225444](https://github.com/GZaccaroni/smart-parking-frontend/commit/1225444d5519ae44d29cd75a9668ed57a7ba5e8f))
* Implemented FindParkingSlots use case ([9a5dcdb](https://github.com/GZaccaroni/smart-parking-frontend/commit/9a5dcdbd5aef2d80d397b618ecbb9523380644cc))
* Implemented FindParkingSlots use case ([5dead58](https://github.com/GZaccaroni/smart-parking-frontend/commit/5dead5834a31c47fcf42a007016f081e51fddf35))
* Implemented FreeParkingSlot use case ([6b8efdb](https://github.com/GZaccaroni/smart-parking-frontend/commit/6b8efdb819a691b86b2be4b4f576a1959c79dc05))
* Implemented Get User use case ([9367ca6](https://github.com/GZaccaroni/smart-parking-frontend/commit/9367ca694577934ae72f693da7d9b595a37a6484))
* Implemented Increment parking slot occupation use case ([9a3d4d3](https://github.com/GZaccaroni/smart-parking-frontend/commit/9a3d4d3e822fcd4f44725de0812a93063a0aa39a))
* Implemented LoginUser use case ([c76af30](https://github.com/GZaccaroni/smart-parking-frontend/commit/c76af30aa289db8f94474ea946dd5bb647147a45))
* Implemented LogoutUser use case ([002bf60](https://github.com/GZaccaroni/smart-parking-frontend/commit/002bf6070e2fc9518b1ba3347b8a6f673f1f9e01))
* Implemented ObserveAuthState Use case ([0d72197](https://github.com/GZaccaroni/smart-parking-frontend/commit/0d721976bb03375ccb2df497eaf1dc5ccecaa124))
* Implemented OccupyParkingSlot use case ([b7afbff](https://github.com/GZaccaroni/smart-parking-frontend/commit/b7afbff63124f8390f934608f67a0b7437905bc0))
* Implemented SignUpUser use case ([9081912](https://github.com/GZaccaroni/smart-parking-frontend/commit/9081912c78f9a7895e881a654a45579372b5f10a))
* Implemented validate user name use case ([c462c37](https://github.com/GZaccaroni/smart-parking-frontend/commit/c462c379fe99aa5aff67046919970cf4ae246894))
* Implemented validate user password use case ([57b7e2e](https://github.com/GZaccaroni/smart-parking-frontend/commit/57b7e2e56cead9f003769ef1ac32a091654182df))
* Implemented ValidateUserEmail use case ([6c0a37a](https://github.com/GZaccaroni/smart-parking-frontend/commit/6c0a37ab173ecb80cabfba6ec02eb18c8fd608cb))
* Implemented ViewParkingSlot use case ([beb45d5](https://github.com/GZaccaroni/smart-parking-frontend/commit/beb45d52325c444d69d62e21d59f41cf1b47bfe1))
* Read app version from version.txt file ([49fab24](https://github.com/GZaccaroni/smart-parking-frontend/commit/49fab2428b4428a85b778e039c12436917883aa1))
* Registered use cases in DI ([fcf513d](https://github.com/GZaccaroni/smart-parking-frontend/commit/fcf513d0b660903a757a217f9019c45d22f529c6))


### Bug Fixes

* Fixed compose launch error ([b454af1](https://github.com/GZaccaroni/smart-parking-frontend/commit/b454af14aebbef6638551a4b27a588fa8077fed3))
* Fixed instrumented tests for data and domain layer ([926ba92](https://github.com/GZaccaroni/smart-parking-frontend/commit/926ba928c0f9ca8dc5b9319a0345903f05c0bf16))
* Fixed sample tests ([778193b](https://github.com/GZaccaroni/smart-parking-frontend/commit/778193b4e416a76421490c74c4932100f1ecb188))
* Fixed SignUpUserTest ([93dea60](https://github.com/GZaccaroni/smart-parking-frontend/commit/93dea6029d7c6a69553390377239c16b933f463a))
* Register presentation module DI ([5bf4cf8](https://github.com/GZaccaroni/smart-parking-frontend/commit/5bf4cf89e664de9bf1bd5083e08c0bd0aa9c8443))


### Code Refactoring

* Changed package name ([f608e3b](https://github.com/GZaccaroni/smart-parking-frontend/commit/f608e3b6e904d15e6c6b0afd56516616a3b5d5dc))
