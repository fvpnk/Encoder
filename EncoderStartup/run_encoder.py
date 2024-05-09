import subprocess
import os

bat_file_path = os.path.join(os.path.dirname(__file__), "EncoderStartupJavaExe.bat")
subprocess.call([bat_file_path])
