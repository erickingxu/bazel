// Copyright 2017 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.skyframe.serialization;

import com.google.devtools.build.lib.skyframe.serialization.strings.StringCodecs;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import java.io.IOException;

/** Common utilities for serialization. */
public class SerializationCommonUtils {
  public static final ImmutableListCodec<String> STRING_LIST_CODEC =
      new ImmutableListCodec<>(StringCodecs.asciiOptimized());

  public static <T> void serializeNullable(T obj, CodedOutputStream out, ObjectCodec<T> codec)
      throws IOException, SerializationException {
    if (obj == null) {
      out.writeBoolNoTag(false);
    } else {
      out.writeBoolNoTag(true);
      codec.serialize(obj, out);
    }
  }

  public static <T> T deserializeNullable(CodedInputStream in, ObjectCodec<T> codec)
      throws IOException, SerializationException {
    return in.readBool() ? codec.deserialize(in) : null;
  }
}
