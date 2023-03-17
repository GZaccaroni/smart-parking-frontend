# Changelog

## [2.1.0](https://github.com/GZaccaroni/smart-parking-frontend/compare/v2.0.0...v2.1.0) (2023-03-17)


### Features

* added apiCall utility method ([3d36070](https://github.com/GZaccaroni/smart-parking-frontend/commit/3d3607003e434540aa8cf107410f9df5dcf5518a))
* added authentication interceptor to data sources ([94859ce](https://github.com/GZaccaroni/smart-parking-frontend/commit/94859ce3a34a2ccc4edf988a06ab030d173ee965))
* api endpoint config in gradle property ([c918c7a](https://github.com/GZaccaroni/smart-parking-frontend/commit/c918c7a9064e8be20c91870c4620605906b57664))
* authentication user dtos ([b8ab642](https://github.com/GZaccaroni/smart-parking-frontend/commit/b8ab642842dec28929c142c8a325bb1df67503a0))
* centered progress indicator ([d5cbc2e](https://github.com/GZaccaroni/smart-parking-frontend/commit/d5cbc2eb4cafc802d986ab0c5d609d0c8b1533fa))
* disable back button while loading ([83d3703](https://github.com/GZaccaroni/smart-parking-frontend/commit/83d370325eff4a46061dc382f0e2f081cfef2aa8))
* distance from geoposition ([c8315f2](https://github.com/GZaccaroni/smart-parking-frontend/commit/c8315f25199fed110cfba87be8c87bb93bac5977))
* handle user session expired error ([709dc2a](https://github.com/GZaccaroni/smart-parking-frontend/commit/709dc2a2c9e13034a5fe492e7a8386f84ea2cfb4))
* invalid parking slot stop end error added ([ec5cba8](https://github.com/GZaccaroni/smart-parking-frontend/commit/ec5cba8e0bb1eecd691292d9d817a1add3d13619))
* InvalidParkingSlotStopEnd error added to AppError entity ([e123619](https://github.com/GZaccaroni/smart-parking-frontend/commit/e1236194629b34a303600c17ecb2a7e51397caec))
* module wide json serializer instance ([4a8b043](https://github.com/GZaccaroni/smart-parking-frontend/commit/4a8b043d4ffaaf17cd5bc65be9cc9866ad750287))
* parking slot ui state ([a31fd77](https://github.com/GZaccaroni/smart-parking-frontend/commit/a31fd77deba1628d2b16687ca4d6b32995b0abf2))
* parking slots map dependencies ([7ae9a22](https://github.com/GZaccaroni/smart-parking-frontend/commit/7ae9a22fc8809c160496d61b23d4bfa155669b60))
* parking slots screen ([3bd3436](https://github.com/GZaccaroni/smart-parking-frontend/commit/3bd3436061b3f8b71aa41e5eb245f6b6ebaddc55))
* parking slots screen view model ([b15a68a](https://github.com/GZaccaroni/smart-parking-frontend/commit/b15a68a5d3269492d8eb87c70ae841f2cd010a9d))
* **parkingslot:** added occupier id to parking slot dto ([33f677a](https://github.com/GZaccaroni/smart-parking-frontend/commit/33f677ac73c2414d2de99252cb99570c52277520))
* **parkingslot:** check page is not loading when calling goBack ([a283ddf](https://github.com/GZaccaroni/smart-parking-frontend/commit/a283ddfbea2495e356d28ee7c0a7cc51b4a64c7a))
* **parkingslot:** free parking slot implementation ([7cc6cad](https://github.com/GZaccaroni/smart-parking-frontend/commit/7cc6cadd301d227c95babe3e60084763abc563c0))
* **parkingslot:** get current parking slot implementation ([d07cc1e](https://github.com/GZaccaroni/smart-parking-frontend/commit/d07cc1ef723e63f355d724a17424d453969cb4a4))
* **parkingslot:** get single parking slot implementation ([1c3cff7](https://github.com/GZaccaroni/smart-parking-frontend/commit/1c3cff71cc8847770b5f2946bd1b48cee9d22ae1))
* **parkingslot:** increment parking slot occupation implementation ([fdc0ef9](https://github.com/GZaccaroni/smart-parking-frontend/commit/fdc0ef950a119ef166efb8c0f5b518171d427d83))
* **parkingslot:** occupy parking slot implementation ([6853f46](https://github.com/GZaccaroni/smart-parking-frontend/commit/6853f46828190cde3d1cddff240d37f8f0b4f5ba))
* **parkingslot:** registered user repository in dependency injection ([99d2444](https://github.com/GZaccaroni/smart-parking-frontend/commit/99d244422c770d9edb34f00292a742401837cbb7))
* **parkingslots:** repository get parking slots given a center and a radius ([63227c9](https://github.com/GZaccaroni/smart-parking-frontend/commit/63227c923ec056c7a003048d01ee3a7338a033ea))
* removed UserParkingSlot ([aedee00](https://github.com/GZaccaroni/smart-parking-frontend/commit/aedee00be99fc57df608af4b4a05ba052854a524))
* store data layer config in separated file ([e608a9c](https://github.com/GZaccaroni/smart-parking-frontend/commit/e608a9cbfb2e8262e46a5a74667834b553a58dc0))
* ticker flow ([8ba78d1](https://github.com/GZaccaroni/smart-parking-frontend/commit/8ba78d169862228b47d18513addfcbe16a5c9cbd))
* use encrypted shared preferences for storing auth token ([07fbdb8](https://github.com/GZaccaroni/smart-parking-frontend/commit/07fbdb8ae75a6f70efb3ea04d5794ff9646c0b95))
* user repository implementation ([5f2bfea](https://github.com/GZaccaroni/smart-parking-frontend/commit/5f2bfeafefb176fd684438517c336afe5ea6f8ed))
* **user:** authentication local data source ([cc950fb](https://github.com/GZaccaroni/smart-parking-frontend/commit/cc950fb7178988ff061781b766130d08c28e8922))
* view current parking slot ([82cbaff](https://github.com/GZaccaroni/smart-parking-frontend/commit/82cbaffa35579649374aa204f69d5928637edf79))
* visibility changed modifier ([d51b2f7](https://github.com/GZaccaroni/smart-parking-frontend/commit/d51b2f7da65ac1382bd9a5114cf53bd5dea9cbd5))


### Bug Fixes

* added missing permissions strings ([7d1b570](https://github.com/GZaccaroni/smart-parking-frontend/commit/7d1b570679fdd6598973e60cc89ebf2610db356a))
* corrected apiCall dispatcher injection ([8254b30](https://github.com/GZaccaroni/smart-parking-frontend/commit/8254b30c75657492d38e56e36adb50905b59e1be))
* open login route if not logged in ([a6bb46e](https://github.com/GZaccaroni/smart-parking-frontend/commit/a6bb46e5ad3b8edc78d7197c3c08de0608149087))
* parking slot screen loader ([e8a4348](https://github.com/GZaccaroni/smart-parking-frontend/commit/e8a434876abb68cf94c5bec5c851e35ae050fc6d))
* **parkingslot:** parking slot data source dto parsing ([d76b80f](https://github.com/GZaccaroni/smart-parking-frontend/commit/d76b80f91ff9800557bfc6b85f8ff98479676cc4))
* remote content media type ([4bca734](https://github.com/GZaccaroni/smart-parking-frontend/commit/4bca734a00f83430471be4dc6187529723d94dd9))
* use appError utility method in view models ([6005cc1](https://github.com/GZaccaroni/smart-parking-frontend/commit/6005cc16ea4166a0dfb8c5b4439419ba3be5e0ef))
* **user:** corrected user data source responses ([f121403](https://github.com/GZaccaroni/smart-parking-frontend/commit/f1214034a2f198b2773448ef74b83e68fabc0730))
* view current parking slot DI registration ([b886be1](https://github.com/GZaccaroni/smart-parking-frontend/commit/b886be1d65ff3fae9f39e8663a05d6a0ca1c5d00))

## [2.0.0](https://github.com/GZaccaroni/smart-parking-frontend/compare/v1.0.0...v2.0.0) (2023-03-01)


### ⚠ BREAKING CHANGES

* incremented android min sdk version
* Changed SignUpUser params
* set DTOs visibility to internal
* Corrected presentation layer classes visibility
* Moved current parking slot away from User info
* Fixed router bug due to StateFlow behavior (distinct)
* Migrated to Kotlin Date from java.util.Date
* Removed ObserveAuthState

### Features

* added AppAlertState to handle alert state ([84b88c6](https://github.com/GZaccaroni/smart-parking-frontend/commit/84b88c6123113ddae37c0c2cfe9f3a1f68b8712c))
* Added Change Password Request Body ([a810550](https://github.com/GZaccaroni/smart-parking-frontend/commit/a810550b34e1656debdbc8f2595c482affdeff68))
* Added delete user method to user data source ([bd0d132](https://github.com/GZaccaroni/smart-parking-frontend/commit/bd0d1328daac53ebda0c6c2ed596bc40c4f59d47))
* added format date method for kotlinx instant ([a259f6f](https://github.com/GZaccaroni/smart-parking-frontend/commit/a259f6f64a9daa714b76ebaeb4b525b18c67720d))
* Added get parking slots and get parking slot method ([09895a4](https://github.com/GZaccaroni/smart-parking-frontend/commit/09895a49213dab54ac09d8b5282afe0ad4067885))
* Added get user method to user data source ([344b595](https://github.com/GZaccaroni/smart-parking-frontend/commit/344b5952d0da479894aa8fc7f71b75326ae75c95))
* Added GetAuthState ([d63256b](https://github.com/GZaccaroni/smart-parking-frontend/commit/d63256b5e0506dde8c78b7391b91a243f9bafa4a))
* added localized parking slot strings ([64b2cee](https://github.com/GZaccaroni/smart-parking-frontend/commit/64b2ceec32f06f726bdf8d6d47247a888a2e6647))
* Added login method to user data source ([16e3749](https://github.com/GZaccaroni/smart-parking-frontend/commit/16e374926e1b35949dabe34116c02b2ca59b9581))
* Added Login Request Body ([ff6f1b6](https://github.com/GZaccaroni/smart-parking-frontend/commit/ff6f1b64bd97f106cae997e7b87e820f4c351e2d))
* Added logout and change password methods ([c3bec26](https://github.com/GZaccaroni/smart-parking-frontend/commit/c3bec26092be696212069683f08d6cb7c8c73a52))
* Added missing errors in data layer ([a6ef778](https://github.com/GZaccaroni/smart-parking-frontend/commit/a6ef7781be22fe84eaf89c7a68e71c08dc6ef780))
* Added Router for better navigation managemtn ([4cef809](https://github.com/GZaccaroni/smart-parking-frontend/commit/4cef8094b33c06e6e1708e23cbe978eeece04335))
* Added sign up method to user data source ([5d52f97](https://github.com/GZaccaroni/smart-parking-frontend/commit/5d52f977f262bcddb644888c1980b40fb8e39dc0))
* added state check in login and sign up screen view models ([e0e8091](https://github.com/GZaccaroni/smart-parking-frontend/commit/e0e809176b34ff74def2b826022ce2cdc7a8bdef))
* added stop end to occupy and increment occupation ([77292a1](https://github.com/GZaccaroni/smart-parking-frontend/commit/77292a19dc0d9e05ab27b5ef7adfc4a5fc4f928f))
* change password screen ([7aa921f](https://github.com/GZaccaroni/smart-parking-frontend/commit/7aa921fc5a405015c3aef8ac2b2b9505f38510f5))
* change password screen view model ([f5915fb](https://github.com/GZaccaroni/smart-parking-frontend/commit/f5915fb791277fdf67bf43d2497eabc1b881f07f))
* check if stop end is valid ([f7c8b62](https://github.com/GZaccaroni/smart-parking-frontend/commit/f7c8b62baeb37154571e25dd4283efa68496a430))
* check new password is different from the old one ([a380911](https://github.com/GZaccaroni/smart-parking-frontend/commit/a3809112f664b76b2202466d18f23646931d759d))
* connected login screen to navigation ([526c283](https://github.com/GZaccaroni/smart-parking-frontend/commit/526c283738c1ef1535ff50370c07faa98e980215))
* Created LoginScreen route ([6b639bd](https://github.com/GZaccaroni/smart-parking-frontend/commit/6b639bdcbb13404275d10b08adeb917dffd1ee2e))
* Created Parking Slot screen route ([2ab6152](https://github.com/GZaccaroni/smart-parking-frontend/commit/2ab61527459feba66b8569dd4f2ba2b147e99291))
* Created parking slots screen route ([3918bbb](https://github.com/GZaccaroni/smart-parking-frontend/commit/3918bbba344125362f352b1467c233f729ca2f47))
* Created Sign Up screen route ([7981898](https://github.com/GZaccaroni/smart-parking-frontend/commit/798189869cf57777fb4f538acac48813ce6ebf1d))
* Implemented error handling with mapping to domain errors ([ad7d70e](https://github.com/GZaccaroni/smart-parking-frontend/commit/ad7d70e1f512d0e9dd3ddef8895bf7a16446e6ae))
* Implemented get parking slot method in data source ([3231a31](https://github.com/GZaccaroni/smart-parking-frontend/commit/3231a319f4e6d54039d19aa2a125f10782bb641d))
* Implemented increment parking slot occupation and  free parking slot ([8df7a8b](https://github.com/GZaccaroni/smart-parking-frontend/commit/8df7a8bbbab0fa70f5b0e5493edd9f13edf1969e))
* implemented occupy parking slot method in datasource ([f5c4ae4](https://github.com/GZaccaroni/smart-parking-frontend/commit/f5c4ae4a82b772a7065bd8fc0e9c020bc9d10559))
* Implemented Snackbar binder ([124666e](https://github.com/GZaccaroni/smart-parking-frontend/commit/124666ed5f8234387fc95e8ded4aec6a13f19e5d))
* keep information of who is occupying a parking slot ([9e5584e](https://github.com/GZaccaroni/smart-parking-frontend/commit/9e5584e7c64496a2d0c60cbf0b989e9e9c0927fa))
* localized app error messages ([c115b9a](https://github.com/GZaccaroni/smart-parking-frontend/commit/c115b9a3072144968b4d04b264bfcc69ea69015a))
* Localized app error messages ([329961f](https://github.com/GZaccaroni/smart-parking-frontend/commit/329961fd2a10d7e78dd46bb6fdc1112f9a14c451))
* login screen UI ([e153d99](https://github.com/GZaccaroni/smart-parking-frontend/commit/e153d99775c61d2628712596f1b41826acf0c016))
* login view model with form submission ([4965b6c](https://github.com/GZaccaroni/smart-parking-frontend/commit/4965b6c998995809ac53ea4449c980115414fad3))
* parking slot screen ([6198378](https://github.com/GZaccaroni/smart-parking-frontend/commit/6198378e4be4227b4a98b84da0316512ac91e648))
* parking slot screen view model ([38e5738](https://github.com/GZaccaroni/smart-parking-frontend/commit/38e57382844c9191fb913f5dd09eb27bc5462474))
* parking slot ui state ([bce4930](https://github.com/GZaccaroni/smart-parking-frontend/commit/bce49305cd95bd5ced9a011ec79f499a00da5185))
* register parking slot view model in DI ([874ca34](https://github.com/GZaccaroni/smart-parking-frontend/commit/874ca3409b6c7a7f6683ec8a613e05e12218469a))
* Registered call methods in retrofit ([05f4ae0](https://github.com/GZaccaroni/smart-parking-frontend/commit/05f4ae07ea3f9e3ce857f1eb1f08fe2425f79d90))
* Registered data sources in dependency injection ([f4f11b2](https://github.com/GZaccaroni/smart-parking-frontend/commit/f4f11b2faea0a940bb6001e8ef749755a8f8e529))
* registered parking slot route ([911295e](https://github.com/GZaccaroni/smart-parking-frontend/commit/911295e0c0f3d4c8f1362a107d8a2475c10d1367))
* Registered routes and updated activity creation ([d7be52b](https://github.com/GZaccaroni/smart-parking-frontend/commit/d7be52b2473c0d44c37d9ffee71dd413dfda64c7))
* registered sign up screen view model in DI ([07e9f71](https://github.com/GZaccaroni/smart-parking-frontend/commit/07e9f71ea1853082dc66cac5fbd16b190cb953c8))
* show content in snackbar ([74188db](https://github.com/GZaccaroni/smart-parking-frontend/commit/74188db8b9fd3150208e8dea37ca549b443b40d7))
* sign up screen ([3741667](https://github.com/GZaccaroni/smart-parking-frontend/commit/37416674ab1a9ccdd79e63f6c720109de8270cb5))
* sign up screen view model ([01e186a](https://github.com/GZaccaroni/smart-parking-frontend/commit/01e186a30d92be1fc5d69144ead7a27b4fc49ac8))
* Updated password validation logic ([93295f4](https://github.com/GZaccaroni/smart-parking-frontend/commit/93295f43f8318bdac74ca6a64f51a025f1a5979f))


### Bug Fixes

* added missing sign up screen strings ([adf7b71](https://github.com/GZaccaroni/smart-parking-frontend/commit/adf7b719d786995121d0c5211c3b4e8baa6104d4))
* added missing strings for change password screen ([960f4e8](https://github.com/GZaccaroni/smart-parking-frontend/commit/960f4e8c34ac21565108bc08bbd5944b448b72be))
* app alert equals code smell ([6247fa8](https://github.com/GZaccaroni/smart-parking-frontend/commit/6247fa8272f9178427ca33941db78881a439e48d))
* Avoid exposing MutableStateFlow in RouterImpl ([7cec491](https://github.com/GZaccaroni/smart-parking-frontend/commit/7cec491149924e88578069d56f88cf5beba7e1c0))
* Corrected GetParking slots body ([2a60d38](https://github.com/GZaccaroni/smart-parking-frontend/commit/2a60d38efb0752c721f8d8de72803d1f4b8e78d9))
* Corrected presentation layer classes visibility ([c6ca809](https://github.com/GZaccaroni/smart-parking-frontend/commit/c6ca8091d2996db63f8191b27f2125593d6d2b27))
* Fixed GetAuthState test ([dba41ee](https://github.com/GZaccaroni/smart-parking-frontend/commit/dba41ee81682f38528714ff027617b27949e676b))
* Fixed router bug due to StateFlow behavior (distinct) ([9446709](https://github.com/GZaccaroni/smart-parking-frontend/commit/9446709d9c3efc0aebb6f1911fea941c452cd227))
* form field errors ([d7f2fb2](https://github.com/GZaccaroni/smart-parking-frontend/commit/d7f2fb2afd121d1b14c826bea3708a042aa59667))
* handle new password equals to current error ([caa90f4](https://github.com/GZaccaroni/smart-parking-frontend/commit/caa90f439df47182e66fd40df3cbb8948c39c0ac))
* improved test to check for valid state behavior ([39e6b7e](https://github.com/GZaccaroni/smart-parking-frontend/commit/39e6b7ecfff93e5c2afee3b06cc0de5928e6f282))
* lowered app alert duration ([1e77638](https://github.com/GZaccaroni/smart-parking-frontend/commit/1e776389d3b012a9a6dd904c6f7f851a41342c9d))
* registered login screen in koin ([5343bb7](https://github.com/GZaccaroni/smart-parking-frontend/commit/5343bb73c3da59a298ea5bd753a74291a242294c))
* Route to correct route based on auth state ([b716ab5](https://github.com/GZaccaroni/smart-parking-frontend/commit/b716ab57d843e46d5d8435c0de3d525b84edd351))
* Router duplicated routes fix ([41c522a](https://github.com/GZaccaroni/smart-parking-frontend/commit/41c522ad8102d269889a2ffb253eda19caa34519))
* set app alert data class ([91e27c5](https://github.com/GZaccaroni/smart-parking-frontend/commit/91e27c5a117f857481e6c10eda019e2c032a166a))
* set DTOs visibility to internal ([9f0457d](https://github.com/GZaccaroni/smart-parking-frontend/commit/9f0457ddcd53c93d1680d1f1c1c4c4dc0273b68a))
* set parking slot screen to be scrollable to support small screens ([e9100d1](https://github.com/GZaccaroni/smart-parking-frontend/commit/e9100d124a5dc8c08d3af9666c812aaec53ab8e1))
* set public visibility to change password screen view model ([673d892](https://github.com/GZaccaroni/smart-parking-frontend/commit/673d892271bdf320d30c7a416a31ff41899f0bfd))
* sign up screen view model ([099f53f](https://github.com/GZaccaroni/smart-parking-frontend/commit/099f53f38931abeb4c9d571253bdd3d4d9ec5fb9))
* sonarcloud code smells ([9ae9690](https://github.com/GZaccaroni/smart-parking-frontend/commit/9ae9690c817a52d388f95bd4cf047fccb4de9857))
* Updated parking slot datasource implementation to match API ([770f731](https://github.com/GZaccaroni/smart-parking-frontend/commit/770f731d90ae00fc0c2d506cfc22e0158d061b17))
* updated usages of snackbarcontent to app alert ([9881734](https://github.com/GZaccaroni/smart-parking-frontend/commit/98817349e14faeae1b8eb36b421045f4ed346cf1))
* Updated user datasource implementation to match API ([dd594e9](https://github.com/GZaccaroni/smart-parking-frontend/commit/dd594e9acb0b0c61af1228c9326a8a0df3b0c59e))


### Code Refactoring

* Changed SignUpUser params ([0369e10](https://github.com/GZaccaroni/smart-parking-frontend/commit/0369e10625cb77cbabe00763717f506f3016a695))
* Migrated to Kotlin Date from java.util.Date ([e953680](https://github.com/GZaccaroni/smart-parking-frontend/commit/e953680fd62b6f92d4bde99a45a36231b2af9e60))
* Moved current parking slot away from User info ([2ae2152](https://github.com/GZaccaroni/smart-parking-frontend/commit/2ae2152046bb7f75cec224dfd3b526711e6c55a1))
* Removed ObserveAuthState ([5da942a](https://github.com/GZaccaroni/smart-parking-frontend/commit/5da942af5e1bce9266415510631dc4fba558130f))


### Build System

* incremented android min sdk version ([13364d1](https://github.com/GZaccaroni/smart-parking-frontend/commit/13364d1cfa67d5e23cea6f881867c751d844d24d))

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
