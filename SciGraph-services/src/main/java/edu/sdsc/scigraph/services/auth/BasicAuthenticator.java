/**
 * Copyright (C) 2014 The SciGraph authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.sdsc.scigraph.services.auth;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class BasicAuthenticator implements Authenticator<BasicCredentials, Subject> {

  private static final Logger logger = Logger.getLogger(BasicAuthenticator.class.getName());
  
  @Override
  public Optional<Subject> authenticate(BasicCredentials credentials) throws AuthenticationException {
    Subject subject = SecurityUtils.getSubject();
    try {
      subject.login(new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword(), false));
      return Optional.of(subject);
    } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
      logger.log(Level.WARNING, e.getMessage(), e);
    } catch (org.apache.shiro.authc.AuthenticationException ae) {
      logger.log(Level.WARNING, ae.getMessage(), ae);
    }
    return Optional.absent();
  }

}
