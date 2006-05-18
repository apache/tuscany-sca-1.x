@REM ----------------------------------------------------------------------------
@REM Copyright 2006 The Apache Software Foundation.
@REM 
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM 
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM 
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM 
@setlocal
@echo off

SET CUR_DIR=%cd%
cd /d "%~dp0.."
SET TUSCANY_HOME=%cd%
cd /d "%CUR_DIR%"
echo Building Tuscany ...
cd /d "%TUSCANY_HOME%"
call mvn -Dtuscany.home=%TUSCANY_HOME% %1 clean install -Dmaven.test.skip=true
if ERRORLEVEL 1 goto error
cd /d "%TUSCANY_HOME%/distribution"
echo Creating Tuscany distribution ...
call mvn %1 clean install
:error
cd /d "%CUR_DIR%"
@endlocal
