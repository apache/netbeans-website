<%/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
%>


<div class="orbit" role="region" aria-label="Apache NetBeans News" data-orbit>
  <div class="orbit-wrapper nb-orbit">
    <div class="orbit-controls">
      <button class="orbit-previous"><span class="show-for-sr">Previous Slide</span>&#9664;&#xFE0E;</button>
      <button class="orbit-next"><span class="show-for-sr">Next Slide</span>&#9654;&#xFE0E;</button>
    </div>
    <ul class="orbit-container">
      <li class="orbit-slide">
          <section class='hero alternate netbeans-bg'>
              <div class='grid-container'>
                  <div class='cell'>
                      <div class='annotation'>Apache Transition News</div>
                      <h1>Apache NetBeans 9.0 Features</h1>
                      <p>
                        Full Java 9 Support, including <b>Jigsaw</b>, <b>JShell</b>, <b>JLink</b> and more.
                      </p>
                      <p><a class='button success' href="/download/nb90/index.html">Features</a></p>
                  </div>
              </div>
          </section>
      </li>
      <li class="orbit-slide">
          <section class='hero alternate netbeans-bg-1'>
              <div class='grid-container'>
                  <div class='cell'>
                      <div class='annotation'>Apache Transition News</div>
                      <h1>Apache NetBeans 9.0 Beta released!</h1>
                      <p>
                        Download the 9.0 beta from an Apache mirror near you.
                      </p>
                      <p><a class='button success' href="/download/nb90/nb90-beta.html">Download</a></p>
                  </div>
              </div>
          </section>
      </li>
      <li class="is-active orbit-slide">
          <section class='hero alternate netbeans-bg'>
              <div class='grid-container'>
                  <div class='cell'>
                      <div class='annotation'>Apache Transition News</div>
                      <h1>Join the Apache NetBeans Community</h1>
                      <p>You can now be part of Apache NetBeans.</p>
                      <p><a class="button success" href="/participate/index.html">Join us!!</a></p>
                  </div>
              </div>
          </section>
      </li>
    </ul>
  </div>
  <nav class="orbit-bullets">
    <button class="is-active" data-slide="0"><span class="show-for-sr">First slide details.</span><span class="show-for-sr">Current Slide</span></button>
    <button data-slide="1"><span class="show-for-sr">Second slide details.</span></button>
    <button data-slide="2"><span class="show-for-sr">Third slide details.</span></button>
  </nav>
</div>
