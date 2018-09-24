package nz.rishaan.hoverchat;

public class Version {
  public int major = 0;
  public int minor = 0;
  public int patch = 1;

  public int id = 1;

public Version (String version) {
  String[] parts = version.split("\\.");
  if (parts.length() != 3) throw new IllegalArgumentException("");
  try {
    major = Integer.parseInt(parts[0]);
    minor = Integer.parseInt(parts[1]);
    patch = Integer.parseInt(parts[2]);
  } catch (NumberFormatException e) {
    System.out.println(e);
  }
}

  public Version (int a, int b, int c) {
    major = a;
    minor = b;
    patch = c;
  }

  @Override
  public String toString () {
    return major + "." + minor + "." + patch;
  }

  public static int update(Version plugin, Version config) {
    if (plugin.major > config.major) {
      return 1;
    } else if (plugin.major == config.major) {
      if (plugin.minor > config.minor) {
        return 1;
      } else if (plugin.minor == config.minor) {
        if (plugin.patch > config.patch) {
          return 1;
        } else if (plugin.patch == config.patch) {
          return 0;
        }
      }
    }
    return -1;
  }

  boolean plugin (Version config, Version plugin) {
    if (plugin.major > config.major) {
      return true;
    } else if (geater.major == config.major) {
      if (plugin.minor > config.minor) {
        return true;
      } else if (plugin.minor = config.minor) {
        if (plugin.minor < config.minor {

        }
      }
    }
    return false;
  }

}
